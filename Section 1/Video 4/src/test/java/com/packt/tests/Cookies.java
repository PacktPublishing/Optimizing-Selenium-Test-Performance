package com.packt.tests;

import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import com.packt.base.BaseTest;

public class Cookies extends BaseTest {

	@Test
	public void addingCookiesTest() {
		// maximize window
		sleep(2000);
		driver.manage().window().maximize();
		
		setCookie();
		sleep(2000);
		
		// Opening page
		driver.get("http://www.echoecho.com/samplecookie1.htm");
		log.info("Page opened!");
		sleep(5000);
		
		// steps of our test
	}


	private void setCookie() {
		log.info("* Adding cookie.");
		driver.get("http://www.echoecho.com/");
		Cookie ck = new Cookie("username", "Dmitry", ".echoecho.com", "/", null);
		driver.manage().addCookie(ck);
		log.info("* Cookie added.");
	}


	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
