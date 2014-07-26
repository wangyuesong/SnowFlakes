package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class editAndDeleteDocument {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testEditAndDeleteDocument() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("link=abcabc");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=myEdit");
		selenium.click("id=vsave");
		selenium.waitForPageToLoad("30000");
		selenium.click("name=10");
		selenium.click("id=confirmDelete");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
