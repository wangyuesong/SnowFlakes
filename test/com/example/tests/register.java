package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class register {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testRegister() throws Exception {
		selenium.open("/SnowFlakes/personalCenter");
		selenium.click("link=Project");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Exit");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=a.text-danger");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=username");
		selenium.type("id=username", "wang");
		selenium.type("id=emailadd", "573978301@qq.com");
		selenium.type("name=password", "ak47b51");
		selenium.type("id=reg-repassword", "ak47b51");
		selenium.click("id=validation");
		selenium.click("id=veri-btn");
		selenium.click("id=uin");
		selenium.click("id=p");
		selenium.type("id=p", "ak47b51");
		selenium.click("id=btlogin");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=validation");
		selenium.type("id=validation", "9237");
		selenium.click("id=emailadd");
		selenium.type("id=emailadd", "573978321@qq.com");
		selenium.click("id=submit");
		selenium.click("id=legal");
		selenium.click("id=submit");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=emailadd");
		selenium.type("id=emailadd", "573978341@qq.com");
		selenium.click("id=validation");
		selenium.click("name=password");
		selenium.type("name=password", "ak47b51");
		selenium.type("id=reg-repassword", "ak47b51");
		selenium.click("id=submit");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
