package com.zzh.uiautomator;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumAndroidParameters {
    public static DesiredCapabilities capabilities;
    public static URL url;
    public static String udid;
    public static String urlServer;
    public static String packageName;
    public static String activityName;

    public static void initParameter(String args[]) {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("unicodeKeyboard", "True");
        capabilities.setCapability("resetKeyboard", "True");
        capabilities.setCapability("newCommandTimeout", "5000");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("automationName", "UIAutomator2");

        if (args.length >= 5) {
            urlServer = args[0];
            udid = args[1];
            packageName = args[2];
            activityName = args[3];
        }
        if (!urlServer.equals("")
                && !udid.equals("")
                && !packageName.equals("")
                && !activityName.equals("")) {
            capabilities.setCapability("appPackage", packageName);
            capabilities.setCapability("appActivity", activityName);
            capabilities.setCapability("deviceName", udid);
            try {
                url = new URL(urlServer);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
