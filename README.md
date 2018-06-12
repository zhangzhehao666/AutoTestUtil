# Uiautomator
使用方法：
1、在project中的build里
allprojects {
		repositories {
			maven { url 'https://www.jitpack.io' }//这句话必须要加
		}
	}
	
  2、在app的build里
 添加依赖：androidTestImplementation 'com.github.zhangzhehao666:Uiautomator:1.5'
