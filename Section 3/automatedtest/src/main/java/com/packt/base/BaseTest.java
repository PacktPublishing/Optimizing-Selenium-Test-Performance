package com.packt.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
	protected WebDriver driver;
	protected HashMap<String, String> testConfig = new HashMap<String, String>();
	protected Logger log;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser", "platform", "environment"})
	protected void setUp(@Optional("chrome") String browser, @Optional String platform, @Optional("local") String environment, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		
		// driver = BrowserDriverFactory.createDriver(browser);
		BrowserDriverFactory factory = new BrowserDriverFactory(browser, platform, testName);
		if (environment.equals("SauceLabs")) {
			driver = factory.createDriverSauce();
		} else {
			driver = factory.createDriver();
		}
		testConfig.put("browser", browser);
		
		// maximize browser window
		driver.manage().window().maximize();
		log = LogManager.getLogger(testName);
	}

	@AfterMethod(alwaysRun = true)
	protected void tearDown() {
		// Closing driver
		log.info("[Closing driver]");
		driver.quit();
	}

	@DataProvider(name = "negativeLogInData")
	public static Object[][] negativeLogInData() {
		return new Object[][] { { "IncorrectUsername", "SuperSecretPassword!", "Your username is invalid!" },
				{ "tomsmith", "IncorrectPassword", "Your password is invalid!" } };
	}

	protected void takeScreenshot(String fileName) {
		if (!testConfig.get("browser").equals("htmlunit")) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "//test-output//screenshots//" + fileName + ".png";
			try {
				FileUtils.copyFile(scrFile, new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	protected List<LogEntry> getBrowserLogs() {
		LogEntries log = driver.manage().logs().get("browser");
		List<LogEntry> logList = log.getAll();
		return logList;
	}

}
