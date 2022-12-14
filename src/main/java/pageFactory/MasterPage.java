package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MasterPage {

	public static WebDriver driver;
	public static ExtentReports extentReport;
	public static ExtentTest extentTest;
	public ITestResult result;
	WebDriverWait driverWait;

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCell cell;

	public static int MEDIUM_WAIT = 10;

	protected static final String SUB_MENU_TITLE_FORMAT = "//span[normalize-space(text())='%s']";

	public void openURL(String url) {
		createReport();
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void openUrlHeadLess(String url) {
		createReport();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("window-size=1920,1080");
		driver = new ChromeDriver(options);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Open browser with headless mode");
	}
	
	// Setup driver with WebDriverManager
	public void openUrlWithFireFox(String url) {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Open browser with firefox");
	}
	

	public void closeURL() {
		driver.close();
		extentTest.log(LogStatus.INFO, "Browser is closed");
	}

	/*
	 * Set up report
	 */

	public void createReport() {
		try {
			extentReport = new ExtentReports("reports//seleniumExtentReports.html", false);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void startTest(String testName) {
		try {
			extentTest = extentReport.startTest(testName);
			System.out.println("Starting a test case: " + testName);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void publishReport() {
		try {
			checkTestResult();
			extentReport.flush();
			extentReport.endTest(extentTest);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void cleanUpOldReport() {
		try {
			File reportFile = new File("reports//seleniumExtentReports.html");
			if (reportFile.delete()) {
				System.out.println("Deleted the file: " + reportFile.getName());
			} else {
				System.out.println("Failed to delete the file.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String captureScreen() {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String dest = null;
		try {
			TakesScreenshot screen = (TakesScreenshot) driver;
			File src = screen.getScreenshotAs(OutputType.FILE);
			dest = System.getProperty("user.dir") + "//screenshots//" + dateName + ".png";
			File target = new File(dest);
			try {
				FileUtils.copyFile(src, target);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return dest;
	}

	public void checkTestResult() {
		System.out.println("Status of execution is:" + extentTest.getRunStatus());
		try {
			if (extentTest.getRunStatus() == LogStatus.PASS) {
				System.out.println("Test case execution status is PASSED");
			} else if (extentTest.getRunStatus() == LogStatus.FAIL) {
				// Do something here
				System.out.println("Test case execution status is FAILURE");
			} else if (extentTest.getRunStatus() == LogStatus.SKIP) {
				System.out.println("Test case execution status is SKIP");
			} else if (extentTest.getRunStatus() == LogStatus.UNKNOWN) {
				System.out.println("Test case execution status is UNKNOWN");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void verifyElementTitleIsDisplay(WebElement ele, String title) {
		if (ele.isDisplayed()) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(), title + " is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					title + " is NOT displayed " + extentTest.addScreenCapture(captureScreen()));
		}
	}

	public void verifyElementIsDisplay(WebElement ele) {
		if (ele.isDisplayed()) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(), "Element is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Element is NOT displayed " + extentTest.addScreenCapture(captureScreen()));
		}
	}

	public String getElementText(WebElement ele) {
		return ele.getText().trim();
	}

	public void enterValueForFieldTitle(WebElement ele, String value, String title) {
		try {
			ele.clear();
			ele.sendKeys(value);
			extentTest.log(LogStatus.PASS, getCurrentMethodName(),
					"Input value " + value + " for field title " + title);

		} catch (Exception e) {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(), "Input value " + value + " for field title " + title
					+ " is FAILED" + extentTest.addScreenCapture(captureScreen()));
			e.printStackTrace();
		}
	}

	/*
	 * Define Click ON Element
	 */
	public void clickOnElementTitle(WebElement ele, String title) {
		waitForObject(ele);
		try {
			ele.click();
			extentTest.log(LogStatus.PASS, getCurrentMethodName(), title + " element is clicked");
		} catch (Exception e) {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Clicked on element " + title + " is FAILED" + extentTest.addScreenCapture(captureScreen()));
			e.printStackTrace();
		}
	}

	public static String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public static String getCurrentClassName() {
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}

	/*
	 * Define Wait for element
	 */

	public void waitForObject(WebElement ele) {
		try {
			driverWait = new WebDriverWait(driver, Duration.ofSeconds(MEDIUM_WAIT));
			driverWait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			driverWait = new WebDriverWait(driver, Duration.ofSeconds(MEDIUM_WAIT));
			driverWait.until(ExpectedConditions.invisibilityOfAllElements(ele));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForObject(By locator) {
		try {
			driverWait = new WebDriverWait(driver, Duration.ofSeconds(MEDIUM_WAIT));
			driverWait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			driverWait = new WebDriverWait(driver, Duration.ofSeconds(MEDIUM_WAIT));
			driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean waitPageIsLoad(int timeout) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean flag = false;
		for (int i = 0; i < timeout; i++) {
			if (js.executeScript("return document.readyState").equals("complete")) {
				flag = true;
				System.out.println("Page is loaded at interation #" + i);
				break;
			}
		}
		return flag;
	}

	public void clickBackToHomePage() {
		WebElement ele = getWebElement("//span[@data-testid='header-logo']");
		scrollUp();
		clickOnElementTitle(ele, "Booking Home Page");
	}

	/*
	 * Define get webelement
	 */

	public WebElement getWebElement(String strXpath) {
		return driver.findElement(By.xpath(strXpath));
	}

	public WebElement getWebElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> getWebElements(String strXpath) {
		return driver.findElements(By.xpath(strXpath));
	}

	public List<WebElement> getWebElements(By locator) {
		return driver.findElements(locator);
	}

	/*
	 * Define for scroll Up/Down
	 */
	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-250)");
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// This will scroll the web page till end.
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollUntilElementIsVislble(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	/*
	 * Define common function
	 */

	public void navigateToMenu(String title) {
		String menuTitle = String.format(SUB_MENU_TITLE_FORMAT, title);
		WebElement ele = getWebElement(menuTitle);
		clickOnElementTitle(ele, title);
		waitPageIsLoad(MEDIUM_WAIT);
	}

	public void clickOnSearchButton() {
		scrollUntilElementIsVislble(getWebElement("//span[normalize-space(text())='Search']"));
		clickOnElementTitle(getWebElement("//span[normalize-space(text())='Search']"), "Search Button");
		waitPageIsLoad(10);
	}

	/*
	 * Return today with format 2022-11-08
	 */
	public String getToday() {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return today;
	}

	// Using to return future date from today
	public String returnFutureDate(int number) {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, number);
		String futureDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		System.out.println("Feature date: " + futureDate);
		return futureDate;
	}

	/*
	 * Define for get Data from excel
	 */

	public void setExcelFile(String excelFilePath) throws IOException {
		// Create an object of File class to open xls file
		File file = new File(excelFilePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		// creating workbook instance that refers to .xls file
		workbook = new XSSFWorkbook(inputStream);

		// creating a Sheet object
		sheet = workbook.getSheetAt(0);

	}

	public String getCellData(String excelFilePath, String testcaseID, String columnTitle) throws IOException {

		setExcelFile(excelFilePath);
		// getting the cell value from rowNumber and cell Number
		cell = sheet.getRow(getRowIndex(testcaseID)).getCell(getColumnIndex(columnTitle));
		cell.setCellType(CellType.STRING);
		// returning the cell value as string
		return cell.getStringCellValue();
	}

	public int getRowIndex(String testCaseID) throws IOException {
		int rowIndex = 0;
		boolean flag = false;
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFCell rowValue = sheet.getRow(i).getCell(0);
			rowValue.setCellType(CellType.STRING);
			if (rowValue.getStringCellValue().equals(testCaseID)) {
				rowIndex = i;
				flag = true;
				break;
			}
		}

		if (!flag) {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(), extentTest.getRunStatus().toString());
			throw new IOException("Test case ID: " + testCaseID + " does not exists on excel file");
		}
		return rowIndex;
	}

	public int getColumnIndex(String columnTitle) throws IOException {
		int columnIndex = 0;
		Iterator<Row> rowIterator = sheet.rowIterator();
		Row headerRow = (Row) rowIterator.next();
		int numberOfCells = headerRow.getPhysicalNumberOfCells();
		boolean flag = false;
		for (int i = 0; i < numberOfCells; i++) {
			XSSFCell cellValue = sheet.getRow(0).getCell(i);
			cellValue.setCellType(CellType.STRING);
			if (cellValue.getStringCellValue().equals(columnTitle)) {
				columnIndex = i;
				flag = true;
				break;
			}
		}

		if (!flag) {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(), extentTest.getRunStatus().toString());
			throw new IOException("Column Title: " + columnTitle + " does not exists on excel file");
		}
		return columnIndex;
	}

}
