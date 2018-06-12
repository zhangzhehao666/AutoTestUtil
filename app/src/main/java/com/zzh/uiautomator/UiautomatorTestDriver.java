package com.zzh.uiautomator;


import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class UiautomatorTestDriver {
    private static int MAX_WAIT_TIMEOUT = 800;
    private int mTimeOut = 1000 * 6;
    private UiDevice mUiDevice;
    private String items = ".*是否允许.*|.*允许.*|.*无响应.*|.*无法访问.*|.*授权.*|.*权限.*|.*位置.*|.*定位.*|.*识别码.*|.*正在尝试.*|"
            + ".*媒体内容.*|.*存储权限.*|.*permission.*|.*其他应用的上层.*";
    private String clickItems = "[确定|OK|允许|.*允许.*|下一步|始终允许|仍然允许|总是允许]{2,4}";
    private String pkg;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public UiautomatorTestDriver(String packageName) {
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mUiDevice = UiDevice.getInstance(mInstrumentation);
        this.pkg = packageName;
    }

    /**
     * 启动App
     */
    public void startApp() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        try {
            if (hasObject(By.text(Pattern.compile(".*新版本.*|.*更新.*")))) {
                clickByRegex(Pattern.compile("以后再说|.*取消.*|.*拒绝.*|.*不更新.*"), pkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化测试，在@BeforeClass函数中调用
     */
    public void startTest() {
        Utils.wakeAndUnlock(InstrumentationRegistry.getTargetContext());
        executor.submit(AuthorityDialogMonitorForUiautomator.getInstance(this));
    }

    /**
     * 结束测试，在@AfterClass函数中调用
     */
    public void endTest() {
        executor.shutdownNow();
        try {
            executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.wakeLock.release();
    }

    /**
     * 上滑
     */
    public void swipeUp() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, y - 200, x / 2, 200, 20);
    }

    /**
     * 下滑
     */
    public void swipeDown() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, 200, x / 2, y - 200, 20);
    }

    /**
     * 左滑
     */
    public void swipeLeft() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x - 100, y / 2, 100, y / 2, 20);
    }

    /**
     * 右滑
     */
    public void swipeRight() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(100, y / 2, x - 100, y / 2, 20);
    }

    /**
     * 不断滑动，直到控件出现
     *
     * @param direction 滑动方向
     * @param resID     控件resourceId
     * @param text      控件text
     *                  以上控件的id和text，必填一个
     * @return false 滑动失败
     */
    public boolean successiveSwipe(String direction, String resID, String text) {
        while (true) {
            if (direction.equals(SwipeDirection.UP)) {
                swipeUp();
            } else if (direction.equals(SwipeDirection.DOWN)) {
                swipeDown();
            } else if (direction.equals(SwipeDirection.LEFT)) {
                swipeLeft();
            } else if (direction.equals(SwipeDirection.RIGHT)) {
                swipeRight();
            }
            UiObject2 object2;
            if (resID == null || resID.equals("")) {
                object2 = mUiDevice.findObject(By.text(text));
            } else {
                object2 = mUiDevice.findObject(By.res(resID));
            }
            if (object2 != null) {
                break;
            }
        }
        return true;
    }

    /**
     * 等待控件出现
     *
     * @param resId   控件resourceID
     * @param timeout 最长等待时间，单位ms
     */
    public void waitForResId(String resId, int timeout) {
        mUiDevice.wait(Until.findObject(By.res(resId)), timeout);
    }

    /**
     * 等待控件出现
     *
     * @param text    控件文本
     * @param timeout 最长等待时间，单位ms
     */
    public void waitForText(String text, int timeout) {
        mUiDevice.wait(Until.findObject(By.text(text)), timeout);
    }

    /**
     * 寻找对应resId的控件
     *
     * @param resId 控件resourceID
     * @return null 不存在此控件
     */
    public UiObject2 findWidgetByResId(String resId) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resId)), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(By.res(resId));

            return object2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 寻找对应resId的控件List
     *
     * @param resId 控件resourceID
     * @return null 不存在此控件
     */
    public List<UiObject2> findWidgetsByResId(String resId) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resId)), mTimeOut);
            return mUiDevice.findObjects(By.res(resId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 寻找文本是text控件
     *
     * @param text 控件文本
     * @return null 不存在此控件
     */
    public UiObject2 findWidgetByText(String text) {
        try {
            mUiDevice.wait(Until.findObject(By.text(text)), mTimeOut);
            return mUiDevice.findObject(By.text(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 寻找文本是text控件List
     *
     * @param text 控件文本
     * @return null 不存在此控件
     */
    public List<UiObject2> findWidgetsByText(String text) {
        try {
            mUiDevice.wait(Until.findObject(By.text(text)), mTimeOut);
            return mUiDevice.findObjects(By.text(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 寻找符合条件的控件
     *
     * @param selector BySelector
     * @return null 不存在此控件
     */
    public UiObject2 findWidgetBySelector(BySelector selector) {
        try {
            mUiDevice.wait(Until.findObject(selector), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(selector);
            return object2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 寻找符合条件的控件
     *
     * @param selector BySelector
     * @return null 不存在此控件
     */
    public List<UiObject2> findWidgetsBySelector(BySelector selector) {
        try {
            mUiDevice.wait(Until.findObject(selector), mTimeOut);
            List<UiObject2> object2List = mUiDevice.findObjects(selector);
            return object2List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 当前页面是否存在符合条件控件
     *
     * @param selector BySelector
     * @return false 不存在此控件
     */
    public boolean hasObject(BySelector selector) {
        try {
            if (mUiDevice.hasObject(selector)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 基于页面位置点击
     *
     * @param x 坐标x
     * @param y 坐标y
     * @return false 点击失败
     */
    public boolean clickByPixel(int x, int y) {
        try {
            mUiDevice.click(x, y);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 点击resourceId控件
     *
     * @param resID 控件resourceId
     * @param index 控件位置（从0开始）
     * @return false 点击失败
     */
    public boolean clickByResId(String resID, int index) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resID)), mTimeOut);
            List<UiObject2> object2List = mUiDevice.findObjects(By.res(resID));
            if (object2List != null && object2List.size() > index) {
                UiObject2 tmp = object2List.get(index);
                tmp.click();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 点击text控件
     *
     * @param regex       正则表达式
     * @param packageName 包名
     * @return true  点击成功
     */
    private boolean clickByRegex(Pattern regex, String packageName) {
        try {
            UiObject2 object2;
            if (packageName == null || packageName.equals("")) {
                object2 = mUiDevice.findObject(By.text(regex).clickable(true));
            } else {
                object2 = mUiDevice.findObject(By.text(regex)
                        .pkg(packageName).clickable(true));
            }
            if (object2 != null) {
                object2.click();
                SystemClock.sleep(1000);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 点击包含text的控件
     *
     * @param text        点击包含文本
     * @param packageName app包名
     * @return false 点击失败
     */
    public boolean clickByContainsText(String text, String packageName) {
        try {
            mUiDevice.wait(Until.findObject(By.textContains(text).pkg(packageName)), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(By.textContains(text).pkg(packageName));
            if (object2 != null) {
                object2.click();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 长按控件resourceId
     *
     * @param resId       控件resourceId
     * @param millisecond 时间（ms）
     */
    public void longClickByResourceId(String resId, int millisecond) {
        int x = findWidgetByResId(resId).getVisibleCenter().x;
        int y = findWidgetByResId(resId).getVisibleCenter().y;
        mUiDevice.swipe(x, y, x, y, millisecond / 5);
    }

    /**
     * 长按控件text
     *
     * @param text        控件text
     * @param millisecond 时间（ms）
     */
    public void longClickByText(String text, int millisecond) {
        int x = findWidgetByText(text).getVisibleCenter().x;
        int y = findWidgetByText(text).getVisibleCenter().y;
        mUiDevice.swipe(x, y, x, y, millisecond / 5);
    }

    /**
     * 对于一个按键按多次
     *
     * @param keyCode 按键
     * @param num     次数
     */
    public void pressTimes(int keyCode, int num) {
        for (int i = 0; i < num; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mUiDevice.pressKeyCode(keyCode);
        }
    }

    /**
     * 清除中文文本
     *
     * @param resId 控件resourceId
     */
    public void clearTextByResourceId(String resId) {
        String name = findWidgetByResId(resId).getText();
        mUiDevice.pressKeyCode(KeyEvent.KEYCODE_MOVE_END);
        //如果光标在最后
        pressTimes(KeyEvent.KEYCODE_DEL, name.length());
        //如果光标在最开始
        // pressTimes(KeyEvent.KEYCODE_FORWARD_DEL, name.length());
    }

    /**
     * 往控件里输入文本
     *
     * @param resId 控件resourceId
     * @param text  输入文本内容
     * @return false 输入失败
     */
    public boolean inputTextByResId(String resId, String text) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resId)), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(By.res(resId));
            if (object2 != null) {
                object2.setText(text);
                return true;
            }
        } catch (Exception e) {
            for (int i = 0; i < text.length(); i++) {
                mUiDevice.pressKeyCode(KeyEvent.keyCodeFromString("KEYCODE_" + text.charAt(i)));
            }
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据坐标位置滑动
     *
     * @param fromX 起始坐标x
     * @param fromY 起始坐标y
     * @param toX   目标坐标x
     * @param toY   目标坐标y
     * @return true 滑动成功
     */
    public boolean swipeByPixel(int fromX, int fromY, int toX, int toY) {
        try {
            return mUiDevice.swipe(fromX, fromY, toX, toY, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 滑动resID控件
     *
     * @param resID     控件resourceId
     * @param direction 滑动的方向
     * @param percent   滑动范围占控件的百分百
     * @param speed     每s滑动的像素点数量
     * @return true     滑动成功
     */
    public boolean swipeByResId(String resID, Direction direction, float percent, int speed) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resID)), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(By.res(resID));
            if (object2 != null) {
                object2.swipe(direction, percent, speed);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据坐标位置拖拽
     *
     * @param fromX 起始坐标x
     * @param fromY 起始坐标y
     * @param toX   目标坐标x
     * @param toY   目标坐标y
     * @return true 拖拽成功
     */
    public boolean dragByPixel(int fromX, int fromY, int toX, int toY) {
        try {
            return mUiDevice.drag(fromX, fromY, toX, toY, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拖拽resID控件
     *
     * @param resID 控件resourceId
     * @param dest  拖拽目的地位置
     * @param speed 每s滑动的像素点数量
     * @return true 拖拽成功
     */
    public boolean dragByResId(String resID, Point dest, int speed) {
        try {
            mUiDevice.wait(Until.findObject(By.res(resID)), mTimeOut);
            UiObject2 object2 = mUiDevice.findObject(By.res(resID));
            if (object2 != null) {
                object2.drag(dest, speed);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 返回
     */
    public void pressBack() {
        try {
            mUiDevice.pressBack();
            SystemClock.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回到桌面
     */
    public void pressHome() {
        try {
            mUiDevice.pressHome();
            SystemClock.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCurrentPackageName() {
        String s = "";
        try {
            s = mUiDevice.getCurrentPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s == null)
            s = "";
        return s;
    }

    //截图
    public void Screenshot() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addTime = sdf.format(date);
        mUiDevice.takeScreenshot(new File("/sdcard/Screenshots/" + addTime + ".png"));
    }

    /**
     * 点击允许权限窗口，已经包含多款手机的不同情况，此方法会自动调用，不需要在测试用例里调用
     */
    public void allowAuthority() {

        if (hasObject(By.text(Pattern.compile(items)))) {

            String pkgSys = getCurrentPackageName();
            if (pkgSys.equalsIgnoreCase(pkg))
                return;
            UiObject2 chkObject;
            try {
                chkObject = mUiDevice.findObject(By.checkable(true).checked(false).pkg(pkgSys));
                if (chkObject != null && !Build.MANUFACTURER.equalsIgnoreCase("LeMobile")) {
                    chkObject.click();
                    Thread.sleep(MAX_WAIT_TIMEOUT / 2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Pattern clickPattern = null;
            try {
                clickPattern = Pattern.compile(clickItems);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (clickPattern != null && clickByRegex(Pattern.compile(clickItems), "")
                    ) {
                try {
                    chkObject = mUiDevice.findObject(By.checkable(true).checked(false).pkg(pkgSys));
                    if (chkObject != null && !Build.MANUFACTURER.equalsIgnoreCase("LeMobile")) {
                        chkObject.click();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2 * MAX_WAIT_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!pkgSys.equals("")
                    && getCurrentPackageName().equals(pkgSys)) {
                pressBack();
            }
        }
    }
}
