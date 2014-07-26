package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class sendInvitation {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testSendInvitation() throws Exception {
		selenium.open("/SnowFlakes/projectMainView/3/show");
		selenium.click("link=Team");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Search new member");
		selenium.type("id=search", "YifanZhang");
		selenium.click("id=ajaxSearch");
		selenium.click("link=Invite");
		assertEquals("An Invitation has been sent!", selenium.getAlert());
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
