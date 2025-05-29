package TestCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PageOneTestCases extends BaseTest{
	@Test
	public void verifyUserDetailsIsCompleted() throws InterruptedException {
		pageOne.selectTitle("Mr.");
		pageOne.setFullName("John Doe");
		pageOne.setDateOfBirth(01,11,1990);
		pageOne.setPhoneNum("6754389654");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		pageOne.setEmailConfirm("gimme@gmail.co.uk");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "NE 12LM");
		pageOne.setContinueButton();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().contains("Page2"));
	}
	@Test
	public void verifyErrorMessages(){
		pageOne.selectTitle("Mrs.");
		pageOne.setFullName("");
		pageOne.setDateOfBirth(01,11,1990);
		pageOne.setPhoneNum("");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		pageOne.setEmailConfirm("");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "");
		pageOne.setContinueButton();
		assertTrue(pageOne.isErrorMessageDisplayed());
		assertTrue(driver.getCurrentUrl().contains("Page1"));
	}

	@DataProvider(name = "loginData")
	public Object[][] getData() throws Exception {
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
			parsedData[i][12] = rawData[i][12]; // assertionFlag
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
	 switch (assertionFlag == null ? "" :assertionFlag) {
		 case "Passed" ->assertTrue(driver.getCurrentUrl().contains("GoodPage2"));
		 case "Failed" -> assertTrue(driver.getCurrentUrl().contains("GoodPage1"));
		 default -> Assert.fail("Invalid assertion flag: " + assertionFlag);

	 }
 }
}
