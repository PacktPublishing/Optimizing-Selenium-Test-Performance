package com.packt.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserDriverFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private String browser;

	public BrowserDriverFactory(String browser) {
		this.browser = browser.toLowerCase();
	}

	public WebDriver createDriver() {
		System.out.println("[Setting up driver: " + browser + "]");

		// Creating driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver.set(new ChromeDriver());
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver.set(new FirefoxDriver());
			break;

		case "ie":
			System.setProperty("webdriver.ie.driver", "src/main/resources/IEDriverServer.exe");
			driver.set(new InternetExplorerDriver());
			break;

		case "phantomjs":
			System.setProperty("phantomjs.binary.path", "src/main/resources/phantomjs.exe");
			driver.set(new PhantomJSDriver());
			break;

		case "htmlunit":
			driver.set(new HtmlUnitDriver());
			break;

		case "headlesschrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			driver.set(new ChromeDriver(chromeOptions));
			break;

		case "headlessfirefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(firefoxBinary);
			driver.set(new FirefoxDriver(firefoxOptions));
			break;

		case "chromeprofile":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=src/main/resources/ChromeProfile");
			driver.set(new ChromeDriver(options));
			break;
			
		case "chromenode":
			String hubUrl = "http://192.168.0.2:4444/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			try {
				driver.set(new RemoteWebDriver(new URL(hubUrl), capabilities));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		return driver.get();
	}

}