package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class deleteProject {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testDeleteProject() throws Exception {
		selenium.open("/SnowFlakes/projectManagement");
		selenium.click("name=7");
		selenium.click("id=confirmDelete");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
