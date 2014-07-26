package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class createDocument {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testCreateDocument() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("link=Create Document");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=title", "12312");
		selenium.type("id=description", "1231");
		selenium.type("id=version", "1");
		selenium.click("id=vsave");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
