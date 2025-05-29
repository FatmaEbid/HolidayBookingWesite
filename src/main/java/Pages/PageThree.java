package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageThree {
	private WebDriver driver;
	private WebDriverWait wait;

	public PageThree(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
private By pageThreeHeader = By.xpath("//h1");
	private By startingdate = By.xpath("//label[contains(text(),'Starting on')]");

	public String getPageThreeHeader() {
		wait.until(ExpectedConditions.stalenessOf(driver.findElement(pageThreeHeader)));
		return driver.findElement(pageThreeHeader).getText();
	}
	public String getStartingDate() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(startingdate));
		return driver.findElement(startingdate).getText();
	}






}
