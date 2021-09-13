package testcases;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.Initialization;
import pageObjects.ObjectsForPages;

public class TopDealsValidation extends Initialization {

	WebDriver driver;
	ObjectsForPages page;
	SoftAssert soft = new SoftAssert();
	private static Logger log = LogManager.getLogger(TopDealsValidation.class.getName());

	@BeforeClass
	public void initilize() throws IOException {
		driver = configuration();
		log.info("The driver is initialized");
	}

	@BeforeMethod
	public void getPageInstance() {
		page = new ObjectsForPages(driver);
	}

	@Test(priority = 0)
	public void dealButtonClick() throws IOException {
		page.getTopDealsButton().click();
		log.debug("The deal's button is clicked");

	}

	@Test(priority = 1)
	public void clickPageButton() throws IOException {

		driver = switchNewWindow();
		// takeScreenShot(driver);
		page = new ObjectsForPages(driver);
		if (page.getpageButton().isDisplayed()) {
			//System.out.println(page.getpageButton().getText());
			log.info("We navigate to the new window");
		} else {
			System.out.println("The element is not found");
		}
	}

	@Test(priority = 2)
	public void checkDisableDefaultsFirstButton() {

		soft.assertTrue(
				checkClickable(page.getFirstPageButton()) && checkClickable(page.getPreviousPageButton())
						&& checkClickable(page.getSelectedButton()),
				"Button First or Previous are anabled by default or 1 page button is not selected");
		log.info("Clickability and selectivity by default is verified");
		soft.assertAll();
	}

	@Test(priority = 3)
	public void checkNumberItemsDefaultOnPage() {
		String numberByDefault = property.getProperty("NumberByDefault");
		Select select = new Select(page.getnumberItemsOnPage());
		String firstNumber = select.getFirstSelectedOption().getText();

		soft.assertTrue(firstNumber.equals(numberByDefault), firstNumber);
		log.info("Number of products by default in field is verified = " + firstNumber);
		soft.assertAll();

	}

	@Test(priority = 4)
	public void checkNumberItemsInTable() {

		Select select = new Select(page.getnumberItemsOnPage());
		String firstNumber = select.getFirstSelectedOption().getText();
		Integer number = page.getNumberItemsInTable().size();
		String value = number.toString();

		soft.assertTrue(value.equals(firstNumber), value + " : " + firstNumber);
		log.info("Number of products by default on the page is verified = " + value);
		soft.assertAll();

	}

	@Test(priority = 5)
	public void checkOrderOfItemsByDefault() {
		List<String> itemsName = new ArrayList<String>();
		List<String> itemsNameDefault = new ArrayList<String>();
		for (WebElement name : page.getNumberItemsInTable()) {
			itemsName.add(name.getText());
			itemsNameDefault.add(name.getText());
		}
		Collections.sort(itemsName);
		Collections.reverse(itemsName);

		soft.assertTrue(itemsName.equals(itemsNameDefault), "The are not equal");
		log.info("the order of products by default is checked");
		soft.assertAll();
	}

	@Test(priority = 6)
	public void checkOrderItemsAfterArrowClick() {
		List<String> itemsName = new ArrayList<String>();
		List<String> itemsNamesAfterArrowClick = new ArrayList<String>();
		page.getOrderItemsArrow().click();
		for (WebElement name : page.getNumberItemsInTable()) {
			itemsName.add(name.getText());
			itemsNamesAfterArrowClick.add(name.getText());
		}
		Collections.sort(itemsName);
		soft.assertTrue(itemsName.equals(itemsNamesAfterArrowClick), "They don't not match");
		log.info("the order of products after arrow click is checked");
		soft.assertAll();
	}

	@Test(priority = 7)
	public void checkEnableButtons() {
		//System.out.println(page.getPageNumberButton().size());
		log.info("The size button's amount is checked and = " + page.getPageNumberButton().size());

	}

	@Test(priority = 8)
	public void getButtonsText() throws IOException {
		int i = 0;
		List<String> list = new ArrayList<String>();
		List<Integer> intList = new ArrayList<Integer>();
		for (WebElement element : page.getPageNumberButton()) {
			String number = element.getText();
			if (!number.isEmpty() && number.length() <= 2) {
				list.add(number);
				intList.add(i++);
			}

		}
		for (String pageNumber : list) {
			driver.findElement(By.xpath("//span[text()='" + pageNumber + "']")).click();
			if (pageNumber.equals("2") || pageNumber.equals("3")) {

				soft.assertTrue(
						page.getFirstButton().isEnabled() && page.getPreviousButton().isEnabled()
								&& page.getNextButton().isEnabled() && page.getLastButton().isEnabled(),
						"Something wrong with buttons enabled on page 2 or 3");

			}
			if (pageNumber.equals("4")) {

				soft.assertTrue(
						!checkClickable(page.getFirstPageButton()) && !checkClickable(page.getPreviousPageButton())
								&& checkClickable(page.getPresenceNextButton())
								&& checkClickable(page.getPresenceLastButton())
								&& checkClickable(page.getSelectedLastPageButton()),
						"Something wrong with buttons enabled on page 4");

			}
			// takeScreenShot(driver);
		}
		log.info("Clickability and selectivity after click to another page buttons is verified");
		soft.assertAll();
	}

	@AfterClass
	public void windowClose() {
		driver.quit();
		log.info("The browser is closed");
	}
}
