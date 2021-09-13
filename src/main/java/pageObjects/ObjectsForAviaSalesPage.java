package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ObjectsForAviaSalesPage {
	
	private WebDriver driver;
	private By airportFromInputField = By.id("travel_from");
	private By airportToInputField = By.id("travel_to");
	private By listAirportsFrom = By.xpath("//div[@id='ajax_listOfOptions']/div");
	private By adWindow = By.xpath("//div[@style='position: absolute; inset: 0px; box-shadow: rgba(0, 0, 0, 0) 0px 0px 0px inset;']");
	private By dateTravelField = By.id("travel_date");
	private By monthsSwitcher = By.cssSelector(".datepicker-switch");
	private By nextMonth = By.cssSelector(".next");
	private By chooseDay = By.cssSelector("div table tbody tr td");
	private By activeDate = By.cssSelector(".active.day");
	private By choosePlusMinusDates = By.id("datebetween");
	private By languageChoose = By.id("travel_language");
	private By travelAirline = By.id("travel_airline");
	private By searchButton = By.cssSelector(".btn.btn-primary");
	
	public ObjectsForAviaSalesPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getAirportFromInputField() {
		return driver.findElement(airportFromInputField);
	}
	
	public List<WebElement> getlistAirportsFrom() {
		return driver.findElements(listAirportsFrom);
	}
	
	public By getListOfAirportsFrom() {
		return listAirportsFrom;
	}
	public By getAdWindow() {
		return adWindow;
	}
	public WebElement getAirportToInputField() {
		return driver.findElement(airportToInputField);
	}
	public WebElement getdateTravel() {
		return driver.findElement(dateTravelField);
	}
	public WebElement getMonthsSwitcher() {
		return driver.findElement(monthsSwitcher);
	}
	public WebElement getNextMonth() {
		return driver.findElement(nextMonth);
	}
	public By getNextMonthElement() {
		return nextMonth;
	}
	public List<WebElement> getChooseDay() {
		return driver.findElements(chooseDay);
	}
	public WebElement getActiveDay() {
		return driver.findElement(activeDate);
	}
	public WebElement getChoosePlusMinusDates() {
		return driver.findElement(choosePlusMinusDates);
	}
	public WebElement getLanguageChoose() {
		return driver.findElement(languageChoose);
	}
	public WebElement getTravelAirline() {
		return driver.findElement(travelAirline);
	}
	public WebElement getSearchButton() {
		return driver.findElement(searchButton);
	}

}
