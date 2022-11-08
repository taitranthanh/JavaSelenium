package tests;


import org.testng.annotations.*;

import pageFactory.MasterPage;
import pageFactory.Bookings.stays.StaysPage;

public class StaysTest extends BaseTest {
	static MasterPage masterPage = new MasterPage();
	StaysPage stayPage;
	
	@BeforeMethod
	public void openBookingUrl() {
		masterPage.openURL("https://www.booking.com/");
		stayPage = new StaysPage(MasterPage.driver);
	}
	
	@Test
	public void verifyUserCouldNotSearchWithEmptyDestination() {
		String testName = "verifyCouldNotLoginWithInvalidEmailFormat";
		masterPage.startTest(testName);
		stayPage.navigateToStaysTab();
		stayPage.clickOnSearchButton();
		stayPage.verifyNoDestinationErrorIsDisplayed();
	}
	
	
	@Test
	public void verifyUserCouldSearchWithDestination() {
		String testName = "verifyUserCouldSearchWithDestination";
		String destination = "Changi Airport";
		masterPage.startTest(testName);
		stayPage.navigateToStaysTab();
		stayPage.selectDestination(destination);
		stayPage.clickOnSearchButton();
	}
	
	@Test
	public void verifyUserCouldSearchWithEnoughInformation() {
		String testName = "verifyUserCouldSearchWithEnoughInformation";
		String destination = "Changi Airport";
		String checkInDate = masterPage.getToday();
		String checkoutDate = masterPage.returnFutureDate(8);
		masterPage.startTest(testName);
		stayPage.selectDestination(destination);
		stayPage.selectCheckInDay(checkInDate);
		stayPage.selectCheckOutDay(checkoutDate);
		stayPage.clickOnSearchButton();
	}
	
	@AfterMethod
	public void tearDown() {
		masterPage.closeURL();
		masterPage.publishReport();
	}

}
