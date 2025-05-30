package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PageOne {
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	public PageOne(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		actions = new Actions(driver);
	}
	private By titleField = By.cssSelector("select[name='title']");
	//private By titleChoice = By.xpath("(//select[@id='title'])[1]");
	private By enterFullName =By.cssSelector("input[id='fullname']");
	private By enterDay = By.id("dob-day");
	private By enterMonth = By.id("dob-month");
	private By enterYear = By.id("dob-year");
	private By phoneField = By.xpath("//input[@id='telephone-number']");
	private By emailOneField = By.cssSelector("input[name='email1']");
	private By emailTwoField = By.cssSelector("input[name='email2']");
	private By addressLineOne = By.cssSelector("input[name='addLine1']");
	private By addressLineTwo = By.cssSelector("input[name='addLine2']");
	private By addressLineThree = By.cssSelector("input[name='addLine3']");
	private By postCodeFeild = By.cssSelector("input[name='postcode2']");
	private By continueButton = By.cssSelector("button[class='primary']");
	private By errorMessages = By.xpath("//h3");
	private By pageOneHeader = By.xpath("//h1");

	public String getPageOneHeader() {
		//wait.until(ExpectedConditions.visibilityOfElementLocated(pageOneHeader));
		//System.out.println(driver.findElement(pageOneHeader).getText());
		return driver.findElement(pageOneHeader).getText();
	}

public boolean isErrorMessageDisplayed() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessages));
	List<WebElement> errorMessagesList = driver.findElements(errorMessages);
		for (WebElement errorMessage : errorMessagesList) {
			if (!errorMessage.isDisplayed()) {
				System.out.println("Error message not displayed");
				return false;
			}System.out.println("Error message: " + errorMessage.getText());
		}return true;
}
	public void selectTitle(String title) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(titleField));
		driver.findElement(titleField).click();
		Select select = new Select(driver.findElement(titleField));
		select.selectByValue(title);
		//select.selectByVisibleText(title);
	}
	public void setFullName(String fullName) {
		driver.findElement(enterFullName).sendKeys(fullName);
	}
	public boolean isNameContainsAlpha() {
		String name= driver.findElement(enterFullName).getAttribute("value");
		if(name != null && name.matches("^[a-zA-Z\\s-]+$")){ // Allow alphabetic characters, spaces, and hyphens
			return true;
		}else {
			throw new IllegalArgumentException("Input must contain only alphabetic characters" + name);
		}
	}
	public void setDateOfBirth(int day, int month, int year) {
		driver.findElement(enterDay).sendKeys(String.valueOf(day));
		driver.findElement(enterMonth).sendKeys(String.valueOf(month));
		driver.findElement(enterYear).sendKeys(String.valueOf(year));
	}
	public void setPhoneNum(String phoneNum) {
		driver.findElement(phoneField).sendKeys(phoneNum);
	}
	public void setEmailAddress(String email1) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailOneField));
		actions.scrollToElement(driver.findElement(emailOneField));
		driver.findElement(emailOneField).sendKeys(email1);
	}
	public String getEmailAddress() {
	return driver.findElement(emailOneField).getText();
	}
	public void setEmailConfirm(String email2) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailTwoField));
		driver.findElement(emailTwoField).sendKeys(email2);
	}
	public void setAddressLineOne(String addressLine1) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
		actions.scrollToElement(driver.findElement(addressLineOne));
				driver.findElement(addressLineOne).sendKeys(addressLine1);
	}
	public void setAddressLineTwo(String addressLine2) {
		//wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineTwo));
		driver.findElement(addressLineTwo).sendKeys(addressLine2);
	}
	public void setAddressLineThree(String addressLine3) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineThree));
		driver.findElement(addressLineThree).sendKeys(addressLine3);
	}
	public void setPostCode(String postCode) {
		actions.scrollToElement(driver.findElement(postCodeFeild));
		driver.findElement(postCodeFeild).sendKeys(postCode);
	}
	public PageTwo setContinueButton() {
		actions.scrollToElement(driver.findElement(continueButton));
		driver.findElement(continueButton).click();
		return new PageTwo(driver);
	}



	public void setCustomerDetails(String addressLine1, String addressLine2, String addressLine3, String postCode) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
		driver.findElement(addressLineOne).sendKeys(addressLine1);
		driver.findElement(addressLineTwo).sendKeys(addressLine2);
		driver.findElement(addressLineThree).sendKeys(addressLine3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(postCodeFeild));
		driver.findElement(postCodeFeild).sendKeys(postCode);

	}

}
