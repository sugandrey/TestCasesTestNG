package testcases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AmaInitialization;
import pageObjects.Initialization;
import pageObjects.ObjectsForAmaHomePage;

public class HomePageAmaCheck extends AmaInitialization {

	ObjectsForAmaHomePage page;
	WebDriver driver;
	SoftAssert soft = new SoftAssert();
	private static Logger log = LogManager.getLogger(HomePageAmaCheck.class.getName());
	
	
	@BeforeTest
	public void initilize() throws IOException {
		driver = configuration();
		log.info("Opening of the browser is executed");
	}

	@BeforeMethod
	public void getPageInstance() {
		page = new ObjectsForAmaHomePage(driver);
	}

	@Test
	public void getAmountBrokenLinksOnPage() {
		List<String> pageLinks = new ArrayList<String>();
		page.getLinks().stream().map(e -> e.getAttribute("href")).distinct().forEach(pageLinks::add);
		for (String link : pageLinks) {
			
			if (!link.isEmpty()) {
				try {
					HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
					connection.setRequestMethod("GET");
					connection.connect();
					int responseStatus = connection.getResponseCode();
					
					soft.assertTrue(responseStatus < 400, responseStatus + " -:- " + link);
					if (responseStatus >= 400) {
						log.error(responseStatus + " -:- " + link);
					}
					
				}

//		for (int i = 0; i < pageLinks.size(); i++) {
//			System.out.println(pageLinks.get(i));
//		}
//		System.out.println(pageLinks.size());
//		for(String string : pageLinks) {
//			try {
//				HttpURLConnection connection = (HttpURLConnection) new URL(string).openConnection();
//				connection.setRequestMethod("HEAD");
//				connection.connect();
//				int responseStatus = connection.getResponseCode();
//				if (responseStatus == 200) {
//					count++;
//					System.out.println(responseStatus + " -:- " + string);
//				}
//				
//			}
				catch (IOException e) {
					e.getMessage();
				}
				
			}

		}
		soft.assertAll();
		
	}
	
	@AfterClass
	public void driverClose() {
		driver.close();
	}

}
