package com.test.DoubanLogin;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * 主要用于登陆，填写验证码，获取cookie，通过selenium登陆
 */
public class LoginMainSelenium {
	private String cookieStr="";
	public String getCookieStr() {
		return cookieStr;
	}
	public LoginMainSelenium (){
		//通过selenium登陆
		System.setProperty("webdriver.gecko.driver",
				"D://development tool//geckodriver-v0.18.0-win32//geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.douban.com/");
		WebElement mailInfo = driver.findElement(By.id("form_email"));
		mailInfo.sendKeys("2423398841@qq.com");
		WebElement pwInfo = driver.findElement(By.id("form_password"));
		pwInfo.sendKeys("guangming123");
		WebElement loginBut = driver.findElement(By.className("bn-submit"));
		loginBut.click();
		//获得cookie
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie cookie : cookies) {
			cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
		}
		driver.close();
	}
}
