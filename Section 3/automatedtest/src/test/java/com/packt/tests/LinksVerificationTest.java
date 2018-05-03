package com.packt.tests;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.packt.base.BaseTest;

public class LinksVerificationTest extends BaseTest {

	@Test
	public void linksVerificationTest() {
		SoftAssert softAssert = new SoftAssert();
		// Opening page
		driver.get("http://the-internet.herokuapp.com/");
		log.info("Page opened!");

		// Get list of links
		List<WebElement> linkElements = driver.findElements(By.xpath("//ul/li/a"));

		// Verify code of each link
		for (WebElement linkElement : linkElements) {
			String linkName = linkElement.getText();
			String url = linkElement.getAttribute("href");
			HttpURLConnection con;
			try {
				con = (HttpURLConnection) new URL(url).openConnection();
				con.setRequestMethod("HEAD");
				int response = con.getResponseCode();
				String responseMessage = con.getResponseMessage();
				log.info("Got " + response + " code with message: '" + responseMessage + "' for '" + linkName + "' ["
						+ url + "].");
				softAssert.assertTrue(response == 200, "Response code for '" + linkName + "' ["
						+ url + "] is not 200. It is: " + response + " code with message: '" + responseMessage + "'");
			} catch (Exception e) {
				softAssert.fail("Couldn't verify '" + linkName + "' ["
						+ url + "]", e);
			}
		}

		sleep(5000);
		softAssert.assertAll();
	}


	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
