package TestCases;

import DataProviders.CsvDataReader;
import DataProviders.JasonDataReader;
import Utilities.ScreenShot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class PageOneTestCases extends BaseTest {




	/**
	 * Test Cases
	 * first name can only enter alpha characters limited to 30 characters
	 * Verifying  customer over 18 to fill out customer details form and accept only numbers
	 * Verifying email address and confirm email address are the same
	 */

	@Test
	public void verifyUserDetailsIsCompleted() throws InterruptedException {
		pageOne.selectTitle("Mr.");
		pageOne.setFullName("John Doe");
		pageOne.setDateOfBirth(01, 11, 1990);
		pageOne.setPhoneNum("6754389654");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		pageOne.setEmailConfirm("gimme@gmail.co.uk");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "NE 12LM");
		pageOne.setContinueButton();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().contains("Page2"));
	}

	@DataProvider
	public Object[][] userData() {
		return new Object[][]{
				{"John Doe", "passed"},
				{"Karen_Peter", "passed"},
				{"Janes345", "failed"},
				{"smi%^th", "failed"},
				{"A very long user name should not exceeds thirty characters", "passed"},
				{"1234567890123456789012345678901", "failed"},
				{"", "failed"}
		};
	}
@Ignore
	@Test(dataProvider = "userData")
	public void verifyUserNameIsAlphaOnly(String fullName, String assertionFlag) {
		pageOne.selectTitle("Mr.");
		pageOne.setFullName(fullName);
		//assertTrue(pageOne.isNameContainsAlpha());
		if ("passed".equals(assertionFlag)) {
			assertTrue(pageOne.isNameContainsAlpha(), "Name should contain only alphabetic characters");
		}
		pageOne.setDateOfBirth(01, 11, 1990);
		pageOne.setPhoneNum("6754389654");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		pageOne.setEmailConfirm("gimme@gmail.co.uk");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "NE 12LM");
		pageOne.setContinueButton();
		try{
			switch (assertionFlag) {
				case "passed" -> assertTrue(driver.getCurrentUrl().contains("Page2"));
				case "failed" -> assertTrue(driver.getCurrentUrl().contains("Page1"));
				default -> Assert.fail("Invalid assertion flag: " + assertionFlag);
			}
		}catch (IllegalArgumentException e) {
			assertEquals(assertionFlag, "failed", "Expected failure for invalid input, but got: " + e.getMessage());
		}
	}

	@Test
	public void verifyErrorMessages() throws InterruptedException {
		pageOne.selectTitle("Mrs.");
		pageOne.setFullName("");
		pageOne.setDateOfBirth(01, 11, 1990);
		pageOne.setPhoneNum("");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		pageOne.setEmailConfirm("");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "");
		Thread.sleep(10000);
		pageOne.setContinueButton();
		assertTrue(pageOne.isErrorMessageDisplayed());
		assertTrue(driver.getCurrentUrl().contains("Page1"));
	}

	@DataProvider(name = "loginData")
	public static Object[][] getData() throws Exception {

		Object[][] rawData = DataProviders.ExcelUtilities.readExcelData("resources/holidayData.xlsx", "Sheet1");
		Object[][] parsedData = new Object[rawData.length][rawData[0].length];
		for (int i = 0; i < rawData.length; i++) {
			parsedData[i][0] = rawData[i][0]; // title
			parsedData[i][1] = rawData[i][1]; // fullName
			parsedData[i][2] = Integer.parseInt((String) rawData[i][2]); // day
			parsedData[i][3] = Integer.parseInt((String) rawData[i][3]); // month
			parsedData[i][4] = Integer.parseInt((String) rawData[i][4]); // year
			parsedData[i][5] = rawData[i][5]; // phoneNum
			parsedData[i][6] = rawData[i][6]; // email1
			parsedData[i][7] = rawData[i][7]; // email2
			parsedData[i][8] = rawData[i][8]; // addressLine1
			parsedData[i][9] = rawData[i][9]; // addressLine2
			parsedData[i][10] = rawData[i][10]; // addressLine3
			parsedData[i][11] = rawData[i][11]; // postCode
			parsedData[i][12] = rawData[i][12]; // assertionFlag*/
		}
		return parsedData;
	}

	@Test(dataProvider = "loginData")
	public void verifyLoginWithDataProvider(String title, String fullName, int day, int month, int year, String phoneNum, String email1, String email2, String addressLine1, String addressLine2, String addressLine3, String postCode, String assertionFlag) {
		pageOne.selectTitle(title);
		pageOne.setFullName(fullName);
		pageOne.setDateOfBirth(day, month, year);
		pageOne.setPhoneNum(phoneNum);
		pageOne.setEmailAddress(email1);
		pageOne.setEmailConfirm(email2);
		pageOne.setCustomerDetails(addressLine1, addressLine2, addressLine3, postCode);
		pageOne.setContinueButton();
		//assertTrue(driver.getCurrentUrl().contains("Page2"));
		switch (assertionFlag == null ? "" : assertionFlag) {
			case "Passed" -> assertTrue(driver.getCurrentUrl().contains("GoodPage2"));
			case "Failed" -> assertTrue(driver.getCurrentUrl().contains("GoodPage1"));
			default -> Assert.fail("Invalid assertion flag: " + assertionFlag);

		}
	}

	@DataProvider(name = "jsonDataProvider")
	public static Iterator<Object[]> jsonDataProvider() {
		List<Map<String, Object>> dataList = JasonDataReader.getTestData("resources/testData.json");
		assert dataList != null;
		return dataList.stream()
				.map(data -> new Object[]{data})
				.iterator();
	}

	@Test(dataProvider = "jsonDataProvider")
	public void testWithJsonData(Map<String, Object> data) throws Exception {
		// Fill the form using the data from JSON
		pageOne.selectTitle((String) data.get("title"));
		pageOne.setFullName((String) data.get("fullName"));
		pageOne.setDateOfBirth(
				Integer.parseInt(data.get("day").toString()),
				Integer.parseInt(data.get("month").toString()),
				Integer.parseInt(data.get("year").toString())
		);
		pageOne.setPhoneNum((String) data.get("phoneNum"));
		pageOne.setEmailAddress((String) data.get("email1"));
		pageOne.setEmailConfirm((String) data.get("email2"));
		pageOne.setCustomerDetails(
				(String) data.get("addressLine1"),
				(String) data.get("addressLine2"),
				(String) data.get("addressLine3"),
				(String) data.get("postCode")
		);
		pageOne.setContinueButton();


		String status = (String) data.get("assertionFlag");
		if ("passed".equalsIgnoreCase(status)) {
			ScreenShot.takeFullPageScreenshot(driver,"testWithJsonData");
			assertTrue(driver.getCurrentUrl().contains("GoodPage2"));
		} else if ("failed".equalsIgnoreCase(status)) {
			assertTrue(driver.getCurrentUrl().contains("GoodPage1"));
		} else {
			Assert.fail("Invalid assertion flag: " + status);
		}
	}
	@DataProvider(name = "csvDataProvider")
	public static Object[][] csvDataProvider() {
		List<String[]> dataList = CsvDataReader.getTestData("resources/testData.csv");
		return dataList.stream()
				.map(data -> new Object[]{data})
				.toArray(Object[][]::new);
	}
	@Test(dataProvider = "csvDataProvider")
	public void testWithCsvData(String[] data) {
		pageOne.selectTitle(data[0]);
		pageOne.setFullName(data[1]);

		pageOne.setDateOfBirth(
				Integer.parseInt(data[2]),
				Integer.parseInt(data[3]),
				Integer.parseInt(data[4])
		);
		pageOne.setPhoneNum(data[5]);
		pageOne.setEmailAddress(data[6]);
		pageOne.setEmailConfirm(data[7]);
		pageOne.setCustomerDetails(data[8], data[9], data[10], data[11]);
		pageOne.setContinueButton();

		String status = data[12];
		if ("passed".equalsIgnoreCase(status)) {
			assertTrue(driver.getCurrentUrl().contains("GoodPage2"));
		} else if ("failed".equalsIgnoreCase(status)) {
			assertTrue(driver.getCurrentUrl().contains("GoodPage1"));
		} else {
			Assert.fail("Invalid assertion flag: " + status);
		}
	}
}
