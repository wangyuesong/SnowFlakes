package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class editTaskAndDeleteTask {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testEditTaskAndDeleteTask() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("//table[@id='taskTable']/tbody/tr[7]/td[6]");
		selenium.click("id=1020");
		selenium.click("id=update-content");
		selenium.type("id=update-content", "abccxvcxv");
		selenium.click("css=div.modal-dialog > div.modal-content > div.modal-body > div.form-group > div.row > div.col-md-6 > select[name=\"target\"]");
		selenium.select("css=div.modal-dialog > div.modal-content > div.modal-body > div.form-group > div.row > div.col-md-6 > select[name=\"target\"]", "label=KaiJiang222");
		selenium.click("css=div.modal-dialog > div.modal-content > div.modal-body > div.form-group > div.row > div.col-md-6 > select[name=\"target\"] > option[value=\"2\"]");
		selenium.click("css=div.modal-dialog > div.modal-content > div.modal-footer > button.btn.btn-primary");
		selenium.waitForPageToLoad("30000");
		selenium.click("xpath=(//a[contains(text(),'Delete')])[6]");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
