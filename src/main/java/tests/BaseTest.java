package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.reporters.HtmlHelper;

import com.relevantcodes.extentreports.LogStatus;

import pageFactory.MasterPage;

public class BaseTest {
	MasterPage masterPage = new MasterPage();

	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		masterPage.cleanUpOldReport();
		masterPage.createReport();
	}

}
