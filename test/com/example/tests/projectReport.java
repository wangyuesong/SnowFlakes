package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class projectReport {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testProjectReport() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("id=animateButton");
		selenium.click("css=#progress-report > div.modal-dialog > div.modal-content > div.modal-footer > button.btn.btn-default");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
