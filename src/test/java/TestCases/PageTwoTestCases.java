package TestCases;

import Pages.PageThree;
import Pages.PageTwo;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PageTwoTestCases extends BaseTest{
	public PageTwo pageTwo;
	PageThree pageThree;
	@Test
	public void setFirstPageAndMoveToNextPage(){
		pageOne.selectTitle("Mr.");
		pageOne.setFullName("John Doe");
		pageOne.setDateOfBirth(01,11,1990);
		pageOne.setPhoneNum("6754389654");
		pageOne.setEmailAddress("gimme@gmail.co.uk");
		String actualEmail = pageOne.getEmailAddress();
		pageOne.setEmailConfirm("gimme@gmail.co.uk");
		pageOne.setCustomerDetails("12 Main street", "Apt 4B", "New York", "NE 12LM");

		pageTwo = pageOne.setContinueButton();
		assertTrue(pageTwo.getSummaryEmail().contains(actualEmail), "Email is not matching");
	}

	@Test(dependsOnMethods = "setFirstPageAndMoveToNextPage")
	public void setHolidaySectionAndMoveToThirdPage(){
		pageTwo.selectHolidayDestinationCityByEnum(PageTwo.destinationCity.BERLIN);
		pageTwo.selectStartDate("22/11/2023");
		pageTwo.selectNumberOfDays("6");
		pageTwo.selectInsurance(2, "no");
		pageThree = pageTwo.clickContinueButton();
		assertEquals(pageThree.getPageThreeHeader(), "Pay for your holiday (page3 of 4)");
	}
	@Test(dependsOnMethods = "setFirstPageAndMoveToNextPage" )
	public void setHolidaySectionAndMoveToFirstPageByEnum(){
		pageTwo.selectHolidayDestinationCityByEnum(PageTwo.destinationCity.LUTON);
		pageTwo.selectStartDate("11/01/2025");
		pageTwo.selectNumberOfDays("5");
		pageTwo.selectInsurance(1, "yes");
		pageOne = pageTwo.clickPreviousButton();
		assertEquals(pageOne.getPageOneHeader(), "Book a Holiday(Page1 of 4)");
	}
/*	@Test(dependsOnMethods = "setFirstPageAndMoveToNextPage" )
	public void setHolidaySectionBySelect(){
		pageTwo.selectHolidayDestBySelect();
		pageTwo.selectHolidayDestinationCityByEnum(PageTwo.destinationCity.BERLIN);
		pageTwo.selectStartDate("11/01/2025");
		pageTwo.selectNumberOfDays("5");
		pageTwo.selectInsurance(1, "yes");
		pageThree = pageTwo.clickContinueButton();
		assertEquals(pageOne.getPageOneHeader(), "Book a Holiday(Page1 of 4)");
	}*/


}
