package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class calendarAssignTask {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testCalendarAssignTask() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("link=Calendar View");
		selenium.waitForPageToLoad("30000");
		selenium.type("name=content", "12412412");
		selenium.select("name=target", "label=KaiJiang222");
		selenium.click("css=button.btn.btn-primary");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
