package com.zzh.uiautomator;

public class AuthorityDialogMonitorForAppium implements Runnable {
    private AppiumAndroidTestDriver testUtil;
    private boolean isTesting;

    private static AuthorityDialogMonitorForAppium authorityDialogMonitor;

    private AuthorityDialogMonitorForAppium(AppiumAndroidTestDriver util) {
        this.testUtil = util;
        this.isTesting = true;
    }

    public static AuthorityDialogMonitorForAppium getInstance(AppiumAndroidTestDriver util) {
        if (authorityDialogMonitor == null) {
            authorityDialogMonitor = new AuthorityDialogMonitorForAppium(util);
        }
        return authorityDialogMonitor;
    }

    public void stopTest() {
        isTesting = false;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && this.isTesting) {
            testUtil.allowAuthority();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
