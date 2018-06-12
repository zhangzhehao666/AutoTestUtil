package com.zzh.uiautomator;


import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import java.util.List;

public class UiautomatorTestDriver {
    public static int mTimeOut = 1000 * 6;
    public static Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
    public static UiDevice mUiDevice = UiDevice.getInstance(mInstrumentation);

    /**
     * 启动App
     */
    public static void startApp(String packageName) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * 上滑
     */
    public static void swipeUp() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, y - 200, x / 2, 200, 20);
    }

    /**
     * 下滑
     */
    public static void swipeDown() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, 200, x / 2, y - 200, 20);
    }

    /**
     * 左滑
     */
    public static void swipeLeft() {
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x - 100, y / 2, 100, y / 2, 20);
    }

    /**
     * 右滑
     */
    public static void swipeRight() {
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
    public static boolean successiveSwipe(String direction, String resID, String text) {
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
    public static void waitForResId(String resId, int timeout) {
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
    public static UiObject2 findWidgetByResId(String resId) {
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
    public static List<UiObject2> findWidgetsByResId(String resId) {
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
    public static UiObject2 findWidgetByText(String text) {
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
    public static List<UiObject2> findWidgetsByText(String text) {
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
    public static UiObject2 findWidgetBySelector(BySelector selector) {
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
    public static List<UiObject2> findWidgetsBySelector(BySelector selector) {
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
    public static boolean hasObject(BySelector selector) {
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
    public static boolean clickByPixel(int x, int y) {
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
    public static boolean clickByResId(String resID, int index) {
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
     * 点击包含text的控件
     *
     * @param text        点击包含文本
     * @param packageName app包名
     * @return false 点击失败
     */
    public static boolean clickByContainsText(String text, String packageName) {
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
    public static void longClickByResourceId(String resId, int millisecond) {
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
    public static void longClickByText(String text, int millisecond) {
        int x = findWidgetByText(text).getVisibleCenter().x;
        int y = findWidgetByText(text).getVisibleCenter().y;
        mUiDevice.swipe(x, y, x, y, millisecond / 5);
    }

    /**
     * 往控件里输入文本
     *
     * @param resId 控件resourceId
     * @param text  输入文本内容
     * @return false 输入失败
     */
    public static boolean inputTextByResId(String resId, String text) {
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
    public static boolean swipeByPixel(int fromX, int fromY, int toX, int toY) {
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
    public static boolean swipeByResId(String resID, Direction direction, float percent, int speed) {
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
    public static boolean dragByPixel(int fromX, int fromY, int toX, int toY) {
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
    public static boolean dragByResId(String resID, Point dest, int speed) {
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
    public static void pressBack() {
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
    public static void pressHome() {
        try {
            mUiDevice.pressHome();
            SystemClock.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
