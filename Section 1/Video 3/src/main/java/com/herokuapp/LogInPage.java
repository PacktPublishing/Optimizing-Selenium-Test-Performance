package com.herokuapp;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.packt.base.BasePageObject;

public class LogInPage extends BasePageObject {
	private String logInPageUrl = "http://the-internet.herokuapp.com/login";

	// private String username = "tomsmith";
	// private String password = "SuperSecretPassword!";

	By usernameLocator = By.id("username");
	By passwordLocator = By.cssSelector("input[name=password]");
	By loginButtonLocator = By.xpath("//button[@type='submit']");
	By errorMessageLocator = By.id("flash");

	public LogInPage(WebDriver driver, HashMap<String, String> testConfig, Logger log) {
		super(driver, testConfig, log);
	}

	public SecurePage logIn(String username, String password) {
		log.info("Entering username and password");
		find(usernameLocator).sendKeys(username);
		find(passwordLocator).sendKeys(password);

		log.info("Clicking Login button");
		find(loginButtonLocator).click();
		
		if (testConfig.get("browser").equals("firefox")) {
			log.info("Additional click needs to be done in firfox");
			// code to click some kind of confirmation after LogIn button clicked.
		}

		return new SecurePage(driver, testConfig, log);
	}

	public void open() {
		openUrl(logInPageUrl);
	}

	public void negativeLogIn(String username, String password) {
		log.info("Entering username and password");
		find(usernameLocator).sendKeys(username);
		find(passwordLocator).sendKeys(password);

		log.info("Clicking Login button");
		find(loginButtonLocator).click();

		waitForErrorMessage();
	}

	private void waitForErrorMessage() {
		waitForVisibilityOf(errorMessageLocator, 5);
	}

	public String getErrorMessageText() {
		return find(errorMessageLocator).getText();
	}

}
