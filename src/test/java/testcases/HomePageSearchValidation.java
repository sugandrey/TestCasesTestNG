package testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.Initialization;
import pageObjects.ObjectsForPages;

public class HomePageSearchValidation extends Initialization {

	ObjectsForPages page;
	WebDriver driver;
	SoftAssert soft = new SoftAssert();
	private static Logger log = LogManager.getLogger(HomePageSearchValidation.class.getName());

	@BeforeMethod
	public void getPageInstance() {
		page = new ObjectsForPages(driver);
	}

	@BeforeTest
	public void initilize() throws IOException {
		driver = configuration();
		log.info("The driver is initialized");
	}

	@DataProvider
	public Object[] getString() {
		String[] randomLetter = new String[5];
		for (int i = 0; i < randomLetter.length; i++) {
			RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
			randomLetter[i] = generator.generate(2);
			//System.out.println(randomLetter[i]);
		}

		return randomLetter;
	}

	@Test(dataProvider = "getString")
	public void getSearchField(String randomLetter) throws InterruptedException, IOException {
		
		List<String> names = new ArrayList<String>();
		// List<String> randomLetters = new ArrayList<String>();
		page.getSearchField().sendKeys(randomLetter);

		Thread.sleep(800);

		if (!page.items().isEmpty()) {
			
			names = page.items().stream().map(s -> s.getText()).collect(Collectors.toList());
			soft.assertTrue(names.stream().anyMatch(s -> s.contains(randomLetter)));
			
//			for (WebElement item : page.items()) {
//				String itemName = item.getText();
//				names.add(itemName);

				// takeScreenShot(driver);
				//System.out.println(randomLetter + " : " + itemName);
//			}
			// randomLetters.add(randomLetter);
//			for (String name : names) {
//				soft.assertTrue(name.contains(randomLetter));
//			}

		}
		page.getSearchField().clear();
		log.info("The characters " + randomLetter + " are inputted!");
		soft.assertAll();
		
	}

	@AfterClass
	public void windowClose() {
		driver.close();
		log.info("The window is closed");
	}

}
