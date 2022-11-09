package tests;


import org.testng.annotations.*;
import pageFactory.MasterPage;
import pageFactory.Bookings.SignInPage;

public class SignInTest extends BaseTest {
	static MasterPage masterPage = new MasterPage();
	SignInPage signInPage;
	String testName;
	
	@BeforeMethod
	public void openBrowser() {
		masterPage.openUrlHeadLess("https://www.booking.com/");
		signInPage = new SignInPage(MasterPage.driver);
	}

	@Test (priority=1)
	public void verifyCouldNotLoginWithoutCredential() {
		testName="verifyCouldNotLoginWithoutCredential";
		masterPage.startTest(testName);
		signInPage.clickSignInButton();
		signInPage.clickContinueWithEmailButton();
		signInPage.verifyNoEmailErrorIsDisplay();
	}
	
	@Test (priority =2)
	public void verifyCouldNotLoginWithInvalidEmailFormat() {
		testName="verifyCouldNotLoginWithInvalidEmailFormat";
		masterPage.startTest(testName);
		signInPage.clickSignInButton();
		signInPage.enterEmailValue("InvalidEmail"); 
		signInPage.clickContinueWithEmailButton();
		signInPage.verifyInvalidEmailErrorIsDisplay();
	}
	
	@Test
	public void verifyUserIsAccessToCreatePasswordPageIfEmailNotRegister() {
		testName="verifyUserIsAccessToCreatePasswordPageIfEmailNotRegister";
		masterPage.startTest(testName);
		signInPage.clickSignInButton();
		signInPage.enterEmailValue("tthanhtai@testing.com");
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
