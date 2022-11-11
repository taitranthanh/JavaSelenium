package tests;

import java.io.IOException;

import org.testng.annotations.*;

import pageFactory.MasterPage;
import pageFactory.Bookings.stays.StaysPage;

public class StaysTest extends BaseTest {
	static MasterPage masterPage = new MasterPage();
	StaysPage stayPage;
	String fileName = "testData/Stays.xlsx";

	@BeforeMethod
	public void openBookingUrl() {
		masterPage.openUrlWithFireFox("https://www.booking.com/");
		stayPage = new StaysPage(MasterPage.driver);
	}

	@Test
	public void TC01_verifyUserCouldNotSearchWithEmptyDestination() {
		String testName = "TC01_verifyUserCouldNotSearchWithEmptyDestination";
		masterPage.startTest(testName);
		stayPage.navigateToStaysTab();
		stayPage.clickOnSearchButton();
		stayPage.verifyNoDestinationErrorIsDisplayed();
	}

	@Test
	public void TC02_verifyUserCouldSearchWithDestination() throws IOException {
		String testName = "TC02_verifyUserCouldSearchWithDestination";
		String destination = stayPage.getCellData(fileName, testName, "Destination");

		masterPage.startTest(testName);
		stayPage.navigateToStaysTab();
		stayPage.selectDestination(destination);
		stayPage.clickOnSearchButton();
	}

	@Test
	public void TC03_verifyUserCouldSearchWithEnoughInformation() throws IOException {
		String testName = "TC03_verifyUserCouldSearchWithEnoughInformation";
		String destination = stayPage.getCellData(fileName, testName, "Destination");
		String checkInDate = masterPage.getToday();
		String checkoutDate = stayPage.getCellData(fileName, testName, "CheckOutDate");

		masterPage.startTest(testName);
		stayPage.selectDestination(destination);
		stayPage.selectCheckInDay(checkInDate);
		stayPage.selectCheckOutDay(checkoutDate);
		stayPage.clickOnSearchButton();
	}

	@AfterMethod
	public void tearDown() {
		masterPage.publishReport();
		masterPage.closeURL();
	}

}
