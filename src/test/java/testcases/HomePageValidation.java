package testcases;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.Initialization;
import pageObjects.ObjectsForPages;

public class HomePageValidation extends Initialization {

	WebDriver driver;
	int chooseSum = 0;
	int cartSum = 0;
	ObjectsForPages page;
	SoftAssert soft = new SoftAssert();
	private static Logger log = LogManager.getLogger(HomePageValidation.class.getName());
	
	@BeforeMethod
	public void getPageInstance() {
		page = new ObjectsForPages(driver);
	}

	@BeforeClass
	public void initilize() throws IOException {
		driver = configuration();
		log.info("The driver is initialized");
	}

	@Test(priority = 0)
	public void numberOfItemsValidation() {

		//System.out.println();
		log.info("Quantity items in the shop " + page.items().size() + " is received");
	}

	@Test(priority = 1)
	public void listOfProductsValidation() {

		List<String> itemsName = new ArrayList<String>();

		for (int i = 0; i < page.items().size(); i++) {
			String[] names = page.items().get(i).getText().split(" ");
			String name = names[0];
			itemsName.add(name);

		}
		FileWriter writer = null;
		try {
			writer = new FileWriter("listOfProducts.txt");
			for (String itemName : itemsName) {
				writer.write(itemName + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		log.info("List of items in onLine shop to 'listOfProducts.txt' file is recorded" );
	}

	@Test(priority = 2)
	public void chooseItemsToCart() throws InterruptedException {

		String[] listChoosenItems = readFile();

		int count = 0;
		for (int i = 0; i < listChoosenItems.length; i++) {

			for (int k = 0; k < page.items().size(); k++) {
				String chooseName = listChoosenItems[i];

				if (page.items().get(k).getText().toUpperCase().contains(chooseName.toUpperCase())) {
					count = getRandomNumber();
					page.getActions().moveToElement(page.getQuantity().get(k)).doubleClick()
							.sendKeys(Integer.toString(count)).build().perform();
					chooseSum += count;
					Thread.sleep(1000);
					page.getProductCard().get(k).click();
					Thread.sleep(1000);

//					page.getQuantity().get(i).clear();
//					
//					page.getQuantity().get(i).sendKeys(Integer.toString(count));
//					chooseSum += count;
//					page.getProductCard().get(k).click();
					break;
					// page.getJs().executeScript("window.scrollBy(0,-3000)");
				}
			}
		}
		log.info("List of products to cart is chosen and amount of products is " + chooseSum);
		//System.out.println("Quantity chosen products " + chooseSum);
	}

	@Test(priority = 3)
	public void clickGoToCartButton() {

		page.getCartButton().click();
		log.debug("Button for basket enter is pushed");
	}

	@Test(priority = 4)
	public void getProductsNumberInCart() throws InterruptedException {

		List<String> digits = new ArrayList<String>();
		for (WebElement number : page.getQuantityInCart()) {
			String[] digit = number.getText().split(" ");
			String amount = digit[0];
			if (!amount.isEmpty()) {
				digits.add(digit[0]);
			}
		}
		for (String number : digits) {
			int value = Integer.parseInt(number);
			cartSum += value;

		}
		//System.out.println("Quantity of products in the cart " + cartSum);
		log.debug("Quantity of products in the cart is received and is " + cartSum);

	}


	@Test(priority = 5)
	public void pushCartButton() throws InterruptedException {

		page.getGoToCartButton().click();
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(Duration.ofSeconds(15));
		fluentWait.pollingEvery(Duration.ofSeconds(1));
		fluentWait.ignoring(NoSuchElementException.class);
		fluentWait.until(new Function<WebDriver, WebElement>() {

			public WebElement apply(WebDriver t) {
				WebElement element = t.findElement(page.getTotalSum());
				return element;
			}
		});
		List<WebElement> names = driver.findElements(By.xpath("//table/tbody/tr/td[2]"));
//		System.out.println(names.size());
//		for (WebElement name : names) {
//			System.out.println(name.getText());
//		}
		page.getJs().executeScript("window.scrollBy(0,-3000)");
		
		log.debug("Navigate to basket. Quantity of products in the basket is checked and is " + names.size());
	}

	@Test(priority = 6)
	public void totalAmountCompare() throws InterruptedException {
		
		int quantity = 0;
		int totalquantity = 0;
		for (WebElement sum : page.getQuantityColumn()) {
			quantity = Integer.parseInt(sum.getText());
			totalquantity += quantity;
		}
			soft.assertTrue(totalquantity==chooseSum, "Quantity items in the shop cart is " + totalquantity + " quant items in the shop " + chooseSum);
		log.debug("Total product's amount in cart and in the basket is compared and " + totalquantity + " -:- " + chooseSum);
		soft.assertAll();
	}

	//@Parameters({ "message" })
	@Test(priority = 7)
	public void setPromoCode() {
		
		String code = property.getProperty("message");
		
		List<String> promoCodes = new ArrayList<String>();
		try {
			promoCodes = readExcelFile("data.xlsx", "Sheet1");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		for (int i = 0; i < promoCodes.size(); i++) {
			page.refreshPage();
			page.getPromocode().sendKeys(promoCodes.get(i));

			page.getPromoApplyButtom().click();

			WebElement element = new WebDriverWait(page.getWebDriver(), 15)
					.until((WebDriver dr1) -> dr1.findElement(page.getPromo()));

			String message = element.getText();
			if (message.contains(code)) {
				//System.out.println(page.getdiscountPercent().getText());
				log.debug("Validity of promocode is checked and discount is " + page.getdiscountPercent().getText());
				page.getPlaceOrderButton().click();
				
			}
		}
		log.debug("Redirected to checkout page is made");
	}

	@Test(priority = 8)
	public void chooseCountry() {
		String country = property.getProperty("country");
		WebElement el = page.getSelectCountry();
		Select element1 = new Select(el);
		element1.selectByVisibleText(country);
		//System.out.println(element1.getFirstSelectedOption().getText());
		log.debug("The country " + element1.getFirstSelectedOption().getText() + " is chosen");
	}

	@Test(priority = 9)
	public void clickAgreeCheckBox() {

		page.getAgreeCheckBox().click();
		log.info("CheckBox is checked");
	}

	@Test(priority = 10)
	public void clickProceedButton() {
		page.getProceedButton().click();
		log.debug("Proceed button is pushed");
	}

	@Test(priority = 11)
	public void checkSuccessText() {

		//System.out.println(page.gettextSuccessOrder().getText());
		log.info("Successful order's text is verified");
	}

	@AfterClass
	public void windowClose() {
		driver.close();
		log.info("The browser is closed");
	}
}
