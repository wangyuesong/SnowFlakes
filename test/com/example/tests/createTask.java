package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class createTask {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testCreateTask() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("css=button.btn.btn-primary");
		selenium.click("name=content");
		selenium.type("name=content", "abc");
		selenium.click("name=target");
		selenium.select("name=target", "label=KaiJiang222");
		selenium.click("css=option[value=\"2\"]");
		selenium.click("id=startdate");
		selenium.click("css=div.modal-body");
		selenium.click("id=duedate");
		selenium.click("css=div.modal-content");
		selenium.click("css=div.modal-footer > button.btn.btn-primary");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
