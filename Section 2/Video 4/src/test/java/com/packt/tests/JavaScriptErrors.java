package com.packt.tests;

import java.util.List;

import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.packt.base.BaseTest;

public class JavaScriptErrors extends BaseTest {

	@Test
	public void javaScriptErrorsTest() {
		SoftAssert softAssert = new SoftAssert();
		
		// Opening page
		//driver.get("http://the-internet.herokuapp.com/javascript_error");
		driver.get("http://the-internet.herokuapp.com/login");
		log.info("Page opened!");
		
		// Verifying there are no JavaScript Errors on the page
		List<LogEntry> logs = getBrowserLogs();
		log.info("Logs: " + logs);
		
		for (LogEntry log : logs) {
			if (log.getLevel().toString().equals("SEVERE")) {
				softAssert.fail("Severe error: " + log.getMessage());
			}
		}
		softAssert.assertAll();
	}
}
