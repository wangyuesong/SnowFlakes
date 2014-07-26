package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class createProject {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testCreateProject() throws Exception {
		selenium.open("/SnowFlakes/projectManagement");
		selenium.click("id=create-btn");
		selenium.click("id=name");
		selenium.type("id=name", "GOU");
		selenium.type("id=category", "GOU");
		selenium.type("id=description", "GOU");
		selenium.type("id=goal", "Gou");
		selenium.click("id=create-confirm");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
