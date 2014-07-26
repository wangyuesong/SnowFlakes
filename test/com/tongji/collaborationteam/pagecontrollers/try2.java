package com.tongji.collaborationteam.pagecontrollers;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class try2 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testTry2() throws Exception {
		selenium.open("/SnowFlakes/index");
		selenium.click("id=email");
		selenium.type("id=email", "573978301@qq.com");
		selenium.type("id=password", "ak47b51");
		selenium.type("id=checking-img", "7199");
		selenium.click("id=email");
		selenium.type("id=email", "573978301@qq.com");
		selenium.type("id=password", "ak47b51");
		selenium.type("id=checking-img", "8621");
		selenium.click("id=login-btn");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=img.img.img-rounded");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=button.btn.btn-primary");
		selenium.click("name=content");
		selenium.type("name=content", "ABC");
		selenium.click("name=target");
		selenium.select("name=target", "label=KaiJiang222");
		selenium.click("css=option[value=\"2\"]");
		selenium.click("id=startdate");
		selenium.click("id=duedate");
		selenium.click("css=div.modal-footer > button.btn.btn-primary");
		selenium.waitForPageToLoad("30000");
	}

//	@After
//	public void tearDown() throws Exception {
//		selenium.stop();
//	}
}
