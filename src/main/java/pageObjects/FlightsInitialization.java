package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlightsInitialization {

	private WebDriver driver;
	private String path = System.getProperty("user.dir");
	public Properties prop = new Properties();

	private void clearCookiesAndMaxWindow() {
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}

	public WebDriver configuration() throws IOException {
		
		FileInputStream input = new FileInputStream(path + "\\src\\main\\resources\\resources\\myData.properties");
		prop.load(input);
		String browserName = prop.getProperty("browser");
		String url = prop.getProperty("urlFlight");
		String browser = System.getProperty("browser");
		
		if (browserName.contains("chrome") || browser.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", path + "\\src\\main\\resources\\resources\\chromedriver.exe");
			ChromeOptions chOpt = new ChromeOptions();
			if (browserName.contains("headless") || browser.contains("headless")) {
				chOpt.addArguments("headless");
			}
			this.driver = new ChromeDriver(chOpt);
			clearCookiesAndMaxWindow();
			driver.get(url);
			return driver;

		}
		if (browserName.contains("firefox") || browser.contains("firefox")) {
			System.setProperty("webdriver.gecko.driver", path + "\\src\\main\\resources\\resources\\geckodriver.exe");
			FirefoxOptions fireOpt = new FirefoxOptions();
			if (browserName.contains("headless") || browser.contains("headless")) {
				fireOpt.addArguments("headless");
			}
			this.driver = new FirefoxDriver(fireOpt);
			clearCookiesAndMaxWindow();
			driver.get(url);
			return driver;
		}
		if (browserName.contains("ie") || browser.contains("ie")) {
			System.setProperty("webdriver.ie.driver", path + "\\src\\main\\resources\\resources\\IEDriverServer.exe");
			InternetExplorerOptions ieOpt = new InternetExplorerOptions();
			this.driver = new InternetExplorerDriver(ieOpt);
			clearCookiesAndMaxWindow();
			driver.get(url);
			return driver;
		}
		if (browserName.contains("edge") || browser.contains("edge")) {
			System.setProperty("webdriver.edge.driver", path + "\\src\\main\\resources\\resources\\msedgedriver.exe");
			EdgeOptions edgeOpt = new EdgeOptions();
			this.driver = new EdgeDriver(edgeOpt);
			clearCookiesAndMaxWindow();
			driver.get(url);
			return driver;
		}
		if (browserName.contains("opera") || browser.contains("opera")) {
			System.setProperty("webdriver.opera.driver", path + "\\src\\main\\resources\\resources\\operadriver.exe");
			OperaOptions operOpt = new OperaOptions();
			if (browserName.contains("headless") || browser.contains("headless")) {
				operOpt.addArguments("headless");
			}	
			this.driver = new OperaDriver(operOpt);
			clearCookiesAndMaxWindow();
			driver.get(url);
			return driver;
		}
		return null;
	}

	
	public WebDriver switchNewWindow() {

		Set<String> windows = driver.getWindowHandles();

		Iterator<String> it = windows.iterator();

		String currentWindow = it.next();
		String childWindow = it.next();
		WebDriver nextDriver = driver.switchTo().window(childWindow);

		return nextDriver;
	}


	public String[] readFile() {

		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\resources\\items.txt";
		String name = "";
		try {
			String content = Files.readString(Paths.get(path));
			System.out.print(content);
			System.out.println();
			String[] names = content.split(",");
			String[] newNames = new String[names.length];
			for (int i = 0; i < names.length; i++) {

				name = names[i].trim();
				newNames[i] = name;

			}
			return newNames;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ArrayList<String> readExcelFile(String fileName, String sheetName) throws IOException {
		ArrayList<String> cellValues = new ArrayList<String>();
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\resources\\" + fileName;
		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				while (rows.hasNext()) {
					Row row = rows.next();
					Cell value = row.getCell(0);
					if (value.getStringCellValue().equalsIgnoreCase("Promocodes")) {
						Iterator<Cell> cell = row.cellIterator();
						cell.next();
						while (cell.hasNext()) {
							Cell PromoValue = cell.next();
							cellValues.add(PromoValue.getStringCellValue());

						}
						break;
					}

				}
			}
		}
		workbook.close();
		return cellValues;

	}

	protected int getRandomNumber() {
		int max = 10;
		int min = 1;
		int randomNumber = (int) (Math.random() * (max - min + 1) + min);

		return randomNumber;
	}
	
	public boolean checkClickable(By clickableElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.presenceOfElementLocated(clickableElement));
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}catch (TimeoutException e) {
			return false;
		}
	}
	
	public WebElement switchDriver(WebDriver oldDriver) {
		WebElement newElement = oldDriver.switchTo().activeElement();
			return newElement;
		
	}

}
