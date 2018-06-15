package com.zzh.uiautomator;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppiumAndroidTestDriver{
	public AndroidDriver<WebElement> driver;
	public String devicePlatform;
	public String udid;
	public String packageName;
	private int findElementTimeout = 60; // seconds

	private ExecutorService executor = Executors.newCachedThreadPool();

	public AppiumAndroidTestDriver() {
		this.driver = new AndroidDriver<WebElement>(AppiumAndroidParameters.url, AppiumAndroidParameters.capabilities);
		this.devicePlatform = "android";
		this.udid = AppiumAndroidParameters.udid;
		this.packageName = AppiumAndroidParameters.packageName;
	}

	/**
	 * 初始化测试，在@BeforeClass函数中调用
	 */
	public void startTest() {
		executor.submit(AuthorityDialogMonitorForAppium.getInstance(this));
	}

	/**
	 * 结束测试，在@AfterClass函数中调用
	 */
	public void endTest() {
		AuthorityDialogMonitorForAppium.getInstance(this).stopTest();
		try {
			executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * 设置超时时间，单位秒(s)
     * @param mTimeOut 执行点击，输入，查找控件时，如果当前没有，会等待mTimeout
     */
    public void setmTimeOut(int mTimeOut) {
		this.findElementTimeout = mTimeOut;
	}
	
	/**
	 * 启动应用
	 */
	public void startTargetApp() {
		this.driver.launchApp();
	}
	
	/**
	 * 先关闭App、再启动APP
	 */
	public void resetTargetApp() {
		this.driver.resetApp();
	}
	
	/**
     * 点击resourceId控件
     * @param resId 控件resourceId
     * @return true 点击成功 
     * @return false 点击失败
     */
	public boolean clickByResId(String resId) {
		WebElement ele = findElement(resId);
		if (ele == null) {
			ele = findElement(resId);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}     
		return true;
	}
	
	/**
     * 点击text控件
     * @param text 控件text
     * @return true 点击成功 
     * @return false 点击失败
     */
	public boolean clickByText(String text) {
		WebElement ele = findElementByUiautomator("new UiSelector().text(\"" + text + "\")", findElementTimeout);
		if (ele == null) {
			ele = findElementByUiautomator("new UiSelector().text(\"" + text + "\")", findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}     
		return true;
	}
	
	private boolean clickByTextMatches(String text, int time) {
		WebElement ele = findElementByUiautomator("new UiSelector().textMatches(\"" + text + "\")", time);
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}     
		return true;
	}
	
	/**
     * 点击包含text控件
     * @param text 控件text
     * @return true 点击成功 
     * @return false 点击失败
     */
	public boolean clickByContainsText(String text) {
		WebElement ele = findElementByUiautomator("new UiSelector().textContains(\"" + text + "\")", findElementTimeout);
		if (ele == null) {
			ele = findElementByUiautomator("new UiSelector().textContains(\"" + text + "\")", findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}     
		return true;
	}
	
	 /**
     * 点击Content-Desc控件
     * @param desc 控件Content-Desc
     * @return true 点击成功
     * @return false 点击失败
     */
	public boolean clickByContentDesc(String desc) {
		WebElement ele = findElementByUiautomator("new UiSelector().description(\"" + desc + "\")", findElementTimeout);
		if (ele == null) {
			ele = findElementByUiautomator("new UiSelector().description(\"" + desc + "\")", findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}     
		return true;
	}
	/**
	 * 点击包含Content-Desc控件
	 * @param desc 控件Content-Desc
	 * @return true 点击成功
	 * @return false 点击失败
	 */
	public boolean clickByContentDescContains(String desc) {
		WebElement ele = findElementByUiautomator("new UiSelector().descriptionContains(\"" + desc + "\")", findElementTimeout);
		if (ele == null) {
			ele = findElementByUiautomator("new UiSelector().descriptionContains(\"" + desc + "\")", findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	 /**
     * 点击xPath控件
     * @param xpath 控件xPath
     * @return true 点击成功
     * @return false 点击失败
     */
	public boolean clickByXpath(String xpath) {
		WebElement ele = findElement(xpath);
		if (ele == null) {
			ele = findElement(xpath);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
     * 点击By属性控件，多用于网页
     * @param by 控件属性
     * @return true 点击成功
     * @return false 点击失败
     */
	public boolean clickBy(By by) {
		WebElement ele = findElement(by, findElementTimeout);
		if (ele == null) {
			ele = findElement(by, findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 基于页面位置点击
	 * @param x 坐标x
	 * @param y	坐标y
	 * @return true 点击成功
	 * @return false 点击失败
	 */
	public boolean clickByCoords(int x, int y) {
		try {
			//driver.performTouchAction(new TouchAction(driver).tap(x,y).release());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 /**
     * 点击允许权限窗口，已经包含多款手机的不同情况
     */
	public void allowAuthority() {
		String items = ".*是否允许.*|.*要允许.*|.*无响应.*|.*无法访问.*|.*授权.*|.*权限.*|.*安全.*|.*位置.*|.*允许.*|.*定位.*|.*相机.*|.*识别码.*|.*正在尝试.*|"
				+ ".*媒体内容.*|.*存储权限.*|.*文件.*|.*permission.*";
		String clickItems = "确定|OK|允许|下一步|始终允许";
		if (driver.getCurrentPackage().equals(this.packageName)) {
			return;
		}
		if (hasElementByTextMatches(items)) {
			WebElement ele = findElementByUiautomator("new UiSelector().checkable(true).checked(false)", 1);
            
			if (ele != null) {
				ele.click();
	            try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				
				}
			}
			
            while (clickByTextMatches(clickItems, 1)
                    ) {
            	ele = findElementByUiautomator("new UiSelector().checkable(true).checked(false)", 1);
                if (ele != null) {
					ele.click();
				}
                try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	private boolean hasElementByTextMatches(String text) {
		WebElement ele = findElementByUiautomator("new UiSelector().textMatches(\"" + text + "\")", 2000);
		if (ele == null) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 往控件里输入文本
	 * @param resId 控件resourceId
	 * @param text	输入文本内容
	 * @return true 输入成功
	 * @return false 输入失败
	 */
	public boolean inputTextByResId(String resId, String text) {
		WebElement ele = findElement(resId);
		if (ele == null) {
			ele = findElement(resId);
		}
		if (ele == null) {
			return false;
		}
		ele.clear();
		ele.sendKeys(text);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
	private static final boolean isChinese(char c) {   
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
	            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
	            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
	            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
	            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
	        return true;  
	    }  
	    return false;  
	}  
	
	/**
	 * 往控件里输入文本
	 * @param xpath 控件xpath
	 * @param text 输入文本内容
	 * @return true 输入成功
	 * @return false 输入失败
	 */
	public boolean inputTextByXpath(String xpath, String text) {
		WebElement ele = findElement(xpath);
		if (ele == null) {
			ele = findElement(xpath);
		}
		if (ele == null) {
			return false;
		}
		ele.clear();
		ele.sendKeys(text);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 根据By属性，输入文本，多用于网页
	 * @param by 搜寻控件条件(org.openqa.selenium.By)
	 * @param text 入文本内容
	 * @return true 输入成功
	 * @return false 输入失败
	 */
	public boolean inputTextBySeleniumBy(By by, String text) {
		WebElement ele = findElement(by, findElementTimeout);
		if (ele == null) {
			ele = findElement(by, findElementTimeout);
		}
		if (ele == null) {
			return false;
		}
		ele.sendKeys(text);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	


	/**
	 * 发送Android KeyCode
	 * @param keyCode {@link io.appium.java_client.android.AndroidKeyCode}
	 */
	public void pressKeyCode(int keyCode) {
		((AndroidDriver)this.driver).pressKeyCode(keyCode);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Back键
	 */
	public void pressBack() {
		this.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
	}
	
	/**
	 * Home键
	 */
	public void pressHome() {
		this.pressKeyCode(AndroidKeyCode.KEYCODE_HOME);
	}
	
	/**
	 * Menu键
	 */
	public void pressMenu() {
		this.pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
	}
	

	public void closeDriver() {
		this.driver.quit();
	}

	private Boolean isIdFormat(String idOrXpath) {
		if (idOrXpath.contains("//") || idOrXpath.contains("["))
			return false;
		else
			return true;
	}

	public WebElement findElement(String idOrXpath) {
		return findElement(idOrXpath, this.findElementTimeout);
	}

	private WebElement findElement(String idOrXpath, int timeout) {
		WebElement ele = null;
		Date startTime = new Date();
		while (true) {
			ele = this.find(idOrXpath);
			if (ele == null) {
				long diff = new Date().getTime() - startTime.getTime();

				if (diff > (timeout * 1000)) {
					break;
				}
			} else {
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return ele;
	}

	private WebElement find(String idOrXpath) {
		WebElement ele = null;
		try {
			if (this.isIdFormat(idOrXpath))
				ele = this.driver.findElementById(idOrXpath);
			else
				ele = this.driver.findElementByXPath(idOrXpath);
		} catch (Exception ex) {
		}
		return ele;
	}
	
	private WebElement findElement(By by, int timeout) {
		WebElement ele = null;
		Date startTime = new Date();
		while (true) {
			ele = this.driver.findElement(by);
			if (ele == null) {
				long diff = new Date().getTime() - startTime.getTime();
				if (diff > (timeout * 1000)) {
					break;
				}
			} else {
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ele;
	}
	
	private WebElement findElementByUiautomator(String uiautomator, int time) {
		WebElement ele = null;
		Date startTime = new Date();
		while (true) {
			try {
				ele = ((AndroidDriver<WebElement>)this.driver).findElementByAndroidUIAutomator(uiautomator);
			} catch (Exception e) {
			}
			if (ele == null) {
				long diff = new Date().getTime() - startTime.getTime();
				if (diff > time * 1000) {
					break;
				}
			} else {
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ele;
	}
}
