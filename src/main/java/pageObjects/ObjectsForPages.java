package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ObjectsForPages {

	private WebDriver driver;
	private Actions actions;
	private JavascriptExecutor js;
	private By itemName = By.xpath("//div/h4");
	private By buyButton = By.xpath("//button[text()='ADD TO CART']");
	private By boughtButton = By.xpath("//button[text()*='ADDED']");
	private By cartButton = By.xpath("//img[@alt='Cart']");
	private By itemsNameInCart = By.xpath("//p[@class='product-name']");
	private By goToCartPageButton = By.xpath("//button[text()='PROCEED TO CHECKOUT']");
	private By itemNameColumn = By.xpath("//table/tbody/tr/td[2]");
	private By itemsQuantityColumn = By.xpath("//tbody/tr/td[3]");
	private By priceColumn = By.xpath("//tbody/tr/td[4]");
	private By quantity = By.cssSelector("input.quantity");
	private By totalAmount = By.cssSelector(".totAmt");
	private By productCard = By.xpath("//div/div/div[3]/button");
	private By promocodeField = By.cssSelector(".promocode");
	private By promoApplyButtom = By.cssSelector(".promoBtn");
	private By promoSuccessOrFailMessage = By.xpath("//span[@class='promoInfo']");
	private By discountPercent = By.cssSelector(".discountPerc");
	private By placeOrderButton = By.xpath("//button[text()='Place Order']");
	private By selectCountry = By.xpath("//select[@style='width: 200px;']");
	private By agreeCheckBox = By.className("chkAgree");
	private By proceedButtton = By.xpath("//button[text()='Proceed']");
	private By textSuccessOrder = By.xpath("//span[@style='color:green;font-size:25px']");
	private By quantityInCart = By.xpath("//ul/li/div[2]/p[1]");
	private By searchItem = By.cssSelector("input.search-keyword");
	private By searchButton = By.className("search-button");
	private By topDealsButton = By.xpath("//a[text()='Top Deals']");
	private By firstPageButton = By.xpath("//span[text()='1']");
	private By firstButton = By.xpath("//a[@aria-label='First']");
	private By previousButton = By.xpath("//a[@aria-label='Previous']");
	private By numberItemsOnPageChoose = By.id("page-menu");
	private By numberItemsInTable = By.xpath("//tbody/tr/td[1]");
	private By orderItemsArrow = By.xpath("//thead/tr/th[1]/span[2]");
	private By pageNumberButton = By.xpath("//div/ul/li/a/span");
	private By nextButton = By.xpath("//a[@aria-label='Next']");
	private By lastButton = By.xpath("//a[@aria-label='Last']");
	private By invisibilityOfFirstButton = By.cssSelector("div ul li:first-child.disabled");
	private By invisibilityOfPreviousButton = By.xpath("//div/ul/li[2][@class='disabled']");
	private By selectedDefaultButton = By.xpath("//div/ul/li[3]/a/span[2]");
	private By selectedLastPageButton = By.xpath("//div/ul/li[5]/a/span[2]");
	private By invisibilityOfNextButton = By.xpath("//div/ul/li[6]");
	private By invisibilityOfLastButton = By.xpath("//div/ul/li[7]");
	
	public ObjectsForPages(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
		// this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public JavascriptExecutor getJs() {
		return js;
	}

	public Actions getActions() {
		return actions;
	}

	public WebDriver getWebDriver() {
		return this.driver;
	}

	
	public List<WebElement> items() {
		return driver.findElements(itemName);

	}

	public List<WebElement> getBuyButton() {
		return driver.findElements(buyButton);
	}

	public WebElement getCartButton() {
		return driver.findElement(cartButton);

	}

	public List<WebElement> getItemsNameInCart() {
		return driver.findElements(itemsNameInCart);
	}

	public WebElement getGoToCartButton() {
		return driver.findElement(goToCartPageButton);
	}

	public List<WebElement> itemsNameTable() {
		return driver.findElements(itemNameColumn);
	}

	public WebDriverWait myWait() {
		return new WebDriverWait(driver, 15);
	}

	public List<WebElement> getQuantityColumn() {
		return driver.findElements(itemsQuantityColumn);
	}

	public List<WebElement> getPriceColumn() {
		return driver.findElements(priceColumn);
	}

	public List<WebElement> getQuantity() {
		return driver.findElements(quantity);
	}

	public WebElement getTotalAmount() {
		return driver.findElement(totalAmount);
	}

	public List<WebElement> getBoughtButton() {
		return driver.findElements(boughtButton);
	}

	public List<WebElement> getProductCard() {
		return driver.findElements(productCard);
	}

	public WebElement getPromocode() {
		return driver.findElement(promocodeField);
	}

	public WebElement getPromoApplyButtom() {
		return driver.findElement(promoApplyButtom);
	}

	public WebElement getPromoSuccessOrFailMessage() {
		return driver.findElement(promoSuccessOrFailMessage);
	}

	public WebElement getdiscountPercent() {
		return driver.findElement(discountPercent);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public WebElement getPlaceOrderButton() {
		return driver.findElement(placeOrderButton);
	}

	public WebElement getSelectCountry() {
		return driver.findElement(selectCountry);
	}

	public WebElement getAgreeCheckBox() {
		return driver.findElement(agreeCheckBox);
	}

	public WebElement getProceedButton() {
		return driver.findElement(proceedButtton);
	}

	public WebElement gettextSuccessOrder() {
		return driver.findElement(textSuccessOrder);
	}

	public By getTotalSum() {
		return totalAmount;
	}

	public List<WebElement> getQuantityInCart() {
		return driver.findElements(quantityInCart);
	}

	public By getPromo() {
		return promoSuccessOrFailMessage;
	}

	public WebElement getSearchField() {
		return driver.findElement(searchItem);
	}

	public WebElement getSearchButton() {
		return driver.findElement(searchButton);
	}

	public WebElement getTopDealsButton() {
		return driver.findElement(topDealsButton);
	}

	public WebElement getpageButton() {
		return driver.findElement(firstPageButton);
	}
	public WebElement getFirstButton() {
		return driver.findElement(firstButton);
	}
	public WebElement getPreviousButton() {
		return driver.findElement(previousButton);
	}
	
	public WebElement getnumberItemsOnPage() {
		return driver.findElement(numberItemsOnPageChoose);
	}
	public List<WebElement> getNumberItemsInTable() {
		return driver.findElements(numberItemsInTable);
	}
	public WebElement getOrderItemsArrow() {
		return driver.findElement(orderItemsArrow);
	}
	public List<WebElement> getPageNumberButton() {
		return driver.findElements(pageNumberButton);
	}
	
	public WebElement getNextButton() {
		return driver.findElement(nextButton);
	}
	
	public WebElement getLastButton() {
		return driver.findElement(lastButton);
	}
	
	public WebElement getInvisibilityOfFirstButton() {
		return driver.findElement(invisibilityOfFirstButton);
	}
	public WebElement getInvisibilityOfPreviousButton() {
		return driver.findElement(invisibilityOfPreviousButton);
	}
	public WebElement getSelectedDefaultButton() {
		return driver.findElement(selectedDefaultButton);
	}
	public WebElement getselectedLastPageButton() {
		return driver.findElement(selectedLastPageButton);
	}
	public WebElement getinvisibilityOfNextButton() {
		return driver.findElement(invisibilityOfNextButton);
	}
	
	public WebElement getinvisibilityOfLastButton() {
		return driver.findElement(invisibilityOfLastButton);
	}
	public By getFirstPageButton() {
		return invisibilityOfFirstButton;
	}

	public By getPreviousPageButton() {
		return invisibilityOfPreviousButton;
	}
	public By getSelectedButton() {
		return selectedDefaultButton;
	}
	public By getPresenceNextButton() {
		return invisibilityOfNextButton;
	}
	public By getPresenceLastButton() {
		return invisibilityOfLastButton;
	}
	public By getSelectedLastPageButton() {
		return selectedLastPageButton;
	}
}
