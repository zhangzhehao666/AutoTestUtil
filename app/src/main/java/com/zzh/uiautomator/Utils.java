package com.zzh.uiautomator;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

public class Utils {
    public static PowerManager.WakeLock wakeLock;

    // 唤醒屏幕解锁
    public static void wakeAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        // 解锁
        kl.disableKeyguard();
        // 点亮屏幕
        wakeLock.acquire();
    }

    //唤醒屏幕解锁
    public static void wake() {
        //获取电源管理器对象
        PowerManager pm;
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        Context context = InstrumentationRegistry.getTargetContext();
        //获取电源管理器对象
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        //点亮屏幕
        wakeLock.acquire();
        SystemClock.sleep(1000);
    }

    //解锁
    public static void unlock() {
        KeyguardManager km;
        KeyguardManager.KeyguardLock kl;
        Context context = InstrumentationRegistry.getTargetContext();
        km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");

        //解锁
        if (km.isKeyguardLocked()) {
            kl.disableKeyguard();
        }
    }

    //判断是否解锁
    public static boolean isUnlockFail() {
        KeyguardManager km;
        Context context = InstrumentationRegistry.getTargetContext();
        km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (km.isKeyguardLocked()) {
            return true;
        }
        return false;
    }
}
