package pageFactory.Bookings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;

import pageFactory.MasterPage;

public class SignInPage extends MasterPage {

	WebDriver driver;
	private String NO_EMAIL_ERROR = "Enter your email address";
	private String INVALID_EMAIL_ERROR = "Make sure the email address you entered is correct.";
	private String EMAIL_TITLE = "Email";
	private String SIGN_IN_TITLE = "Sign In";
	private String CONTINUE_WITH_EMAIL_TITLE = "Continue With Email";
	private String CREATE_PASSWORD_TITLE = "Create password";
	private String CREATE_ACCOUNT_TITLE = "Create account";

	@FindBy(xpath = "//div[@class='bui-group__item']//span[normalize-space()='Sign in']")
	@CacheLookup
	WebElement signInBtn;

	@FindBy(id = "username")
	WebElement emailFieldEle;

	@FindBy(xpath = "//button[normalize-space()='Continue with email']")
	WebElement continueWithEmailBtn;

	@FindBy(id = "username-note")
	WebElement errorTitleEle;

	@FindBy(xpath = "//div[@class='page-header']/h1")
	WebElement createPassHeaderEle;

	@FindBy(id = "new_password")
	WebElement newPasswordFieldEle;

	@FindBy(id = "confirmed_password")
	WebElement confirmPasswordFieldEle;

	@FindBy(xpath = "//button[normalize-space()='Create account']")
	WebElement createAccountBtn;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSignInButton() {
		clickOnElementTitle(signInBtn, SIGN_IN_TITLE);
	}

	public void clickContinueWithEmailButton() {
		clickOnElementTitle(continueWithEmailBtn, CONTINUE_WITH_EMAIL_TITLE);
		waitPageIsLoad(5);
	}

	public void verifyNoEmailErrorIsDisplay() {
		waitForObject(errorTitleEle);
		verifyElementTitleIsDisplay(errorTitleEle, "Error Message");
		String currentError = getElementText(errorTitleEle);
		if (currentError.equals(NO_EMAIL_ERROR)) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(),
					"Validate successed: " + NO_EMAIL_ERROR + " error is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Validate FAILED, Expected error is  " + NO_EMAIL_ERROR + " but error " + currentError
							+ " is displayed " + extentTest.addScreenCapture(captureScreen()));
		}
	}

	public void verifyInvalidEmailErrorIsDisplay() {
		verifyElementTitleIsDisplay(errorTitleEle, "Error Message");
		String currentError = getElementText(errorTitleEle);
		if (currentError.equals(INVALID_EMAIL_ERROR)) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(),
					"Validate successed: " + INVALID_EMAIL_ERROR + " error is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Validate FAILED, Expected error is  " + INVALID_EMAIL_ERROR + " but error " + currentError
							+ " is displayed " + extentTest.addScreenCapture(captureScreen()));
		}
	}

	public void enterEmailValue(String value) {
		enterValueForFieldTitle(emailFieldEle, value, EMAIL_TITLE);
	}

	/*
	 * Define function for create password
	 */

	public void verifyCreatePasswordHeaderIsDisplay() {
		waitForObject(createPassHeaderEle);
		verifyElementTitleIsDisplay(createPassHeaderEle, CREATE_PASSWORD_TITLE);
		String header = getElementText(createPassHeaderEle);
		if (header.equals(CREATE_PASSWORD_TITLE)) {
			extentTest.log(LogStatus.PASS, getCurrentMethodName(),
					"Validate successed: " + CREATE_PASSWORD_TITLE + " title is displayed");
		} else {
			extentTest.log(LogStatus.FAIL, getCurrentMethodName(),
					"Validate FAILED, Expected is  " + CREATE_PASSWORD_TITLE + " but current is " + header
							+ " is displayed " + extentTest.addScreenCapture(captureScreen()));
		}
	}

	public void verifyEnterNewPasswordFieldIsDisplay() {
		verifyElementTitleIsDisplay(newPasswordFieldEle, "New Password Field");
	}

	public void verifyConfirmNewPasswordFieldIsDisplay() {
		verifyElementTitleIsDisplay(confirmPasswordFieldEle, "Confirm Password Field");
	}

	public void verifyCreateAccountButtonIsDisplay() {
		verifyElementTitleIsDisplay(createAccountBtn, CREATE_ACCOUNT_TITLE);
	}

}
