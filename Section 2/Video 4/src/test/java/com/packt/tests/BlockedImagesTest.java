package com.packt.tests;

import org.testng.annotations.Test;

import com.packt.base.BaseTest;

public class BlockedImagesTest extends BaseTest {

	@Test
	public void blockedImagesTest() {
		sleep(3000);

		// Opening page
		driver.get("http://automationpractice.com/index.php");
		log.info("Page opened!");

		// Continue test steps here

		sleep(15000);
	}


	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
