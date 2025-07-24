package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class PageTwo {
	private WebDriver driver;
	private WebDriverWait wait;
	private Select select;
	private WebElement element;

	public PageTwo(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	private By summaryeEmail = By.xpath("(//p[@size='4'])[4]");
	private By holidayDestinationCity = By.cssSelector("select[name='holiday']");
	private By selectNumberOfDays = By.cssSelector("select[id='holidayNights']");
	private By radioButtonforInsurance = By.cssSelector("input.radios__input");
	private By continueButton = By.cssSelector("button[value='continue']");
	private By previousButton = By.cssSelector("button[value='back']");
	private By startDateField = By.cssSelector("input[id='startDate']");


	public String getSummaryEmail() {
		return driver.findElement(summaryeEmail).getText();
	}

	public enum destinationCity {
		BARCELONA("Barcelona. (£45 a night)"),
		LONDON("London. (£75 a night)"),
		PARIS("Paris. (£65 a night)"),
		LUTON("Luton. (£65 a night)"),
		RIGA("Riga. (£35 a night)"),
		BERLIN("Berlin. (£55 a night)"),
		NEWYORK("New York. (£125 a night)"),
		CASABLANCA("Casablanca. (£25 a night)"),
		NEWDELHI("New Delhi. (£45 a night)");

		private String value;

		destinationCity(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}


	public void selectHolidayDestinationCityByEnum(destinationCity destinationCity) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(holidayDestinationCity)));
		select = new Select(driver.findElement(holidayDestinationCity));
		select.selectByVisibleText(destinationCity.getValue());
	}

	public void selectHolidayDestBySelect() {
		select = new Select(driver.findElement(holidayDestinationCity));
		select.selectByValue("Barcelona. (£45 a night)");

	}


	public void selectStartDate(String date) {
		WebElement startDate = driver.findElement(startDateField);
		startDate.click();
		startDate.sendKeys(date);
	}

	public String getStartDate() {
		WebElement startDate = driver.findElement(startDateField);
		wait.until(ExpectedConditions.visibilityOf(startDate));
		return startDate.getAttribute("value");
	}

	public void selectNumberOfDays(String days) {
		WebElement numberOfDays = driver.findElement(selectNumberOfDays);
		wait.until(ExpectedConditions.visibilityOf(numberOfDays));
		select = new Select(numberOfDays);
		List<WebElement> options = select.getOptions();
		boolean isOptionValid = options.stream()
				.anyMatch(option -> option.getAttribute("value").equals(days));
		if (!isOptionValid) {
			throw new IllegalArgumentException("Invalid number of days entered, you entered : " + days + " The valid options from 1 to 10");
		}
		selectHoliday(numberOfDays, days);
	}

	public void selectInsurance(int buttonNum, String isInsuranceRequired) {
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(radioButtonforInsurance));
		List<WebElement> insuranceCheckBox = driver.findElements(radioButtonforInsurance);
		WebElement selectButton = insuranceCheckBox.get(buttonNum - 1);  // button =1 =no

		if (buttonNum < 1 || buttonNum > insuranceCheckBox.size()) {
			throw new IllegalArgumentException("Invalid button number: " + buttonNum + ". Valid range is 1 to " + insuranceCheckBox.size());
		}

		String currentStatus = selectButton.getAttribute("value");  // status = o


		if (isInsuranceRequired.equalsIgnoreCase(currentStatus)) {
			selectButton.click();
		} else //if (buttonNum < 1 || buttonNum > insuranceCheckBox.size() || buttonNum != selectButton.getText().length() || currentStatus != isInsuranceRequired) {
			throw new IllegalArgumentException("Invalid insurance selected " + currentStatus);
	}

	public PageThree clickContinueButton() {
		driver.findElement(continueButton).click();
		return new PageThree(driver);
	}

	public PageOne clickPreviousButton() {
		driver.findElement(previousButton).click();
		return new PageOne(driver);
	}


	private void selectHoliday(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}




}