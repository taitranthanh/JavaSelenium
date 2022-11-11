package tests;

import java.io.IOException;

import org.testng.annotations.*;
import pageFactory.MasterPage;
import pageFactory.Bookings.SignInPage;

public class SignInTest extends BaseTest {
	static MasterPage masterPage = new MasterPage();
	SignInPage signInPage;
	String testName;
	String filePath = "testData//signIn.xlsx";

	@BeforeMethod
	public void openBrowser() throws IOException {
		masterPage.openUrlHeadLess("https://www.booking.com/");
		signInPage = new SignInPage(MasterPage.driver);
	}

	@Test(priority = 1)
	public void TC01_verifyCouldNotLoginWithoutCredential() {
		testName = "TC01_verifyCouldNotLoginWithoutCredential";
		masterPage.startTest(testName);
		signInPage.clickSignInButton();
		signInPage.clickContinueWithEmailButton();
		signInPage.verifyNoEmailErrorIsDisplay();
	}

	@Test(priority = 2)
	public void TC02_verifyCouldNotLoginWithInvalidEmailFormat() throws IOException {
		testName = "TC02_verifyCouldNotLoginWithInvalidEmailFormat";
		masterPage.startTest(testName);
		String userName = signInPage.getCellData(filePath, testName, "Username");

		signInPage.clickSignInButton();
		signInPage.enterEmailValue(userName);
		signInPage.clickContinueWithEmailButton();
		signInPage.verifyInvalidEmailErrorIsDisplay();
	}

	@Test(priority = 3)
	public void TC03_verifyUserIsAccessToCreatePasswordPageIfEmailNotRegister() throws IOException {
		testName = "TC03_verifyUserIsAccessToCreatePasswordPageIfEmailNotRegister";
		masterPage.startTest(testName);

		String userName = signInPage.getCellData(filePath, testName, "Username");

		signInPage.clickSignInButton();
		signInPage.enterEmailValue(userName);
		signInPage.clickContinueWithEmailButton();
		signInPage.verifyCreatePasswordHeaderIsDisplay();
		signInPage.verifyEnterNewPasswordFieldIsDisplay();
		signInPage.verifyConfirmNewPasswordFieldIsDisplay();
		signInPage.verifyCreateAccountButtonIsDisplay();
	}

	@AfterMethod
	public void tearDown() {
		masterPage.publishReport();
		masterPage.closeURL();
	}

}
