package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ObjectsForAmaHomePage {
	
	private WebDriver driver;
	private By links = By.cssSelector("a[href*='/']");
	
	
	public ObjectsForAmaHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public List<WebElement> getLinks() {
		return driver.findElements(links);
	}
	
	

}
