# Uiautomator
使用方法：
1、在project中的build里
allprojects {
		repositories {
			maven { url 'https://www.jitpack.io' }//这句话必须要加
		}
	}
  2、在app的build里
  androidTestImplementation 'com.android.support.test:rules:1.0.2'
