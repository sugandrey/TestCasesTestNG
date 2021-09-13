package testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.FlightsInitialization;
import pageObjects.ObjectsForAviaSalesPage;

public class FlightFieldsValidation extends FlightsInitialization {

	ObjectsForAviaSalesPage page;
	WebDriver driver;
	SoftAssert soft = new SoftAssert();
	private static Logger log = LogManager.getLogger(FlightFieldsValidation.class.getName());
	
	
	@BeforeTest
	public void initilize() throws IOException {
		
		driver = configuration();
		log.info("Opening of the browser is executed");
	}

	@BeforeMethod
	public void getPageInstance() {
		page = new ObjectsForAviaSalesPage(driver);
		
	}

	@Test(priority = 0)
	public void airportFromFieldClick() {
		page.getAirportFromInputField().click();
		log.debug("Cursor is navigated to text field From");
	}

	@Test(priority = 1)
	public void airportFromSearchCheck() {

		String name = prop.getProperty("airportFrom");
		page.getAirportFromInputField().sendKeys(name);
		log.debug("The letters for airport From search are inputted");
	}

	@Test(priority = 2)
	public void checkListAirsFrom() {
		
		List<String>airportsSearchFrom = new ArrayList<String>();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(page.getListOfAirportsFrom()));

		for (WebElement name : page.getlistAirportsFrom()) {
			airportsSearchFrom.add(name.getText());
		}
		for (String name : airportsSearchFrom) {
			String lowName = name.toLowerCase();
			
				soft.assertTrue(lowName.contains(prop.getProperty("airportFrom")), lowName);
		}
		System.out.println(page.getlistAirportsFrom().size());
		
		soft.assertAll();
		log.info("The correct list of airports From is checked!");
	}
	
	@Test(priority = 3)
	public void airportArrivalChoose() {
		page.getlistAirportsFrom().get(page.getlistAirportsFrom().size() - 1).click();
		log.debug("An airport From is selected");
	}
	
	@Test(priority = 4)
	public void airportToFieldClick() {
		page.getAirportToInputField().click();
		log.debug("Cursor is navigated to text field To");
	}
	
	@Test(priority = 5)
	public void airportToSearchCheck() {

		String name = prop.getProperty("airportTo");
		page.getAirportToInputField().sendKeys(name);
		log.debug("The letters for airport To search are inputted");
	}
	
	
	@Test(priority = 6)
	public void checkListAirsTo() {
		List<String>airportsSearchTo = new ArrayList<String>();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(page.getListOfAirportsFrom()));

		for (WebElement name : page.getlistAirportsFrom()) {
			airportsSearchTo.add(name.getText());
		}
		for (String name : airportsSearchTo) {
			String lowName = name.toLowerCase();
			
				String message = "The search is not working properly";
				soft.assertTrue(lowName.contains(prop.getProperty("airportTo")), message);
			
		}
		System.out.println(page.getlistAirportsFrom().size());
		soft.assertAll();
		log.debug("The letters for airport search are inputted");
	}
	
	@Test(priority = 7)
	public void airportDestinationChoose() {
		page.getlistAirportsFrom().get(page.getlistAirportsFrom().size() - 1).click();
		log.debug("An airport To is selected");
	}
	
	@Test(priority = 9)
	public void dateTripChoose() {
		String choosday = null;
		page.getdateTravel().click();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(page.getNextMonthElement()));
		
		while(!page.getMonthsSwitcher().getText().contains(prop.getProperty("chooseMonth"))) {
			page.getNextMonth().click();
		}
		
		for(WebElement day: page.getChooseDay()) {
			if(day.getText().equalsIgnoreCase(prop.getProperty("chooseDay"))) {
				choosday = day.getText();
				day.click();
				break;
			}
		}
		log.debug("A Date of trip is selected and = " + choosday);
	}
	
	@Test(priority = 8)
	public void activeDateCheck() {	
			page.getdateTravel().click();
			
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(page.getNextMonthElement()));
			
			String day = page.getActiveDay().getText();
			String month = page.getMonthsSwitcher().getText();
			page.getActiveDay().click();
			System.out.println(day + " " + month);
			log.info("The active date is verified");
	}
	
	@Test(priority = 9)
	public void plusMinusDatesChoose() {
		
		Select dropMenu = new Select(page.getChoosePlusMinusDates());
		dropMenu.selectByVisibleText(prop.getProperty("chooseBetween"));
		log.debug("The choice of plus minus date is selected");
	}
	
	@Test(priority = 10)
	public void languageChoose() {
		
		Select dropMenu = new Select(page.getLanguageChoose());
		dropMenu.selectByVisibleText(prop.getProperty("chooseLanguage"));
		log.debug("The choice of a language is selected");
	}
	
	@Test(priority = 11)
	public void chooseTravelAirline() {
		
		Select dropMenu = new Select(page.getTravelAirline());
		dropMenu.selectByVisibleText(prop.getProperty("airline"));
		log.debug("The choice of an airline is selected");
	}
	
	@Test(priority = 12)
	public void buttonSearchClick() throws IOException {
		page.getSearchButton().click();
		log.debug("The button search is pushed");
		//init.takeScreenShot(driver);
	}
	
	
	@AfterTest(enabled=false)
	public void popupListener() {
		
		WebDriverWait wait = new WebDriverWait(driver, 120);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(page.getAdWindow()));
		WebElement adElement = driver.switchTo().activeElement();
		adElement.findElement(page.getAdWindow()).click();
	}
	@AfterClass
	public void driverClose() {
		driver.close();
		log.info("The browser is closed");
	}
}
