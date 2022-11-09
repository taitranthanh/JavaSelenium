package pageFactory.Bookings.stays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import pageFactory.MasterPage;

public class StaysPage extends MasterPage {
	WebDriver driver;
	private String STAYS_TITLE = "Stays";
	private String DESTINATION_TITLE = "Destination";
	private String NO_DESTINATION_ERROR_TITLE = "Enter a destination to start searching.";
	private String DATE_FORMAT = "//td[@data-date='%s']";

	@FindBy(id = "ss")
	WebElement destinationFieldEle;

	@FindBy(xpath = "//div[@class='fe_banner__message']")
	WebElement destinationErrorEle;

	public StaysPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navigateToStaysTab() {
		navigateToMenu(STAYS_TITLE);
	}

	/*
	 * Define input function
	 */
	public void selectDestination(String value) {
		enterValueForFieldTitle(destinationFieldEle, value, DESTINATION_TITLE);
		try {
			String ele = String.format("//li//span[normalize-space()='%s']", value);
			WebElement option = getWebElement(ele);
			waitForObject(option);
			scrollUntilElementIsVisible(option);
			option.click();
			extentTest.log(LogStatus.PASS, getCurrentMethodName(), value + " is selected");

		} catch (Exception e) {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					extentTest.addScreenCapture(captureScreen()) + value + " is not selected");
		}
	}
	
	// Using format
	public void selectCheckInDay(String date) {
		WebElement ele = getWebElement(String.format(DATE_FORMAT, date));
		clickOnElementTitle(ele, "Checkin date "+date);
	}
	
	public void selectCheckOutDay(String date) {
		WebElement ele = getWebElement(String.format(DATE_FORMAT, date));
		clickOnElementTitle(ele, "Checkout date "+date);
	}

	/*
	 * Define verify error function
	 */
	public void verifyNoDestinationErrorIsDisplayed() {
		verifyElementIsDisplay(destinationErrorEle);
		String currentError = getElementText(destinationErrorEle);
		if (currentError.contains(NO_DESTINATION_ERROR_TITLE)) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(),
					"Validate successed: " + NO_DESTINATION_ERROR_TITLE + " error is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Validate FAILED, Expected error is  " + NO_DESTINATION_ERROR_TITLE + " but Current error is: "
							+ currentError + extentTest.addScreenCapture(captureScreen()));
		}
	}
}
