package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class personalCenter {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testPersonalCenter() throws Exception {
		selenium.open("/SnowFlakes/personalCenter");
		selenium.click("id=edit");
		selenium.click("id=phone");
		selenium.type("id=phone", "18817598842");
		selenium.click("id=birthday");
		selenium.click("id=save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Task List");
		selenium.click("id=1017");
		selenium.click("css=div.modal-footer > button.btn.btn-default");
		selenium.click("link=15Message Box");
		selenium.click("id=11");
		selenium.click("id=message-back-btn");
		selenium.click("link=Invitation");
		selenium.click("link=Modify Password");
		selenium.click("link=Setting");
		selenium.click("id=emailSetting");
		selenium.click("id=SettingChange");
		assertEquals("Success!", selenium.getAlert());
		selenium.click("id=emailSetting");
		selenium.click("id=SettingChange");
		assertEquals("Success!", selenium.getAlert());
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
