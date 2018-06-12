package com.zzh.uiautomator;

public class AuthorityDialogMonitorForUiautomator implements Runnable {
    private UiautomatorTestDriver testUtil;

    private static AuthorityDialogMonitorForUiautomator authorityDialogMonitor;

    private AuthorityDialogMonitorForUiautomator(UiautomatorTestDriver util) {
        this.testUtil = util;
    }

    public static AuthorityDialogMonitorForUiautomator getInstance(UiautomatorTestDriver util) {
        if (authorityDialogMonitor == null) {
            authorityDialogMonitor = new AuthorityDialogMonitorForUiautomator(util);
        }

        return authorityDialogMonitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            testUtil.allowAuthority();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
