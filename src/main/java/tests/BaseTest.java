package tests;

import org.testng.annotations.BeforeSuite;

import pageFactory.MasterPage;

public class BaseTest {
	MasterPage masterPage = new MasterPage();

	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		masterPage.cleanUpOldReport();
		masterPage.createReport();
	}

}
