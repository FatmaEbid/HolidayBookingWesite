package TestCases;

import Pages.PageOne;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import io.github.sridharbandi.AxeRunner;
import io.github.sridharbandi.HtmlCsRunner;  // accessibility testing library
import io.github.sridharbandi.a11y.AxeTag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {
	public  WebDriver driver;
	public Properties properties;
	public String timestamp;
	public PageOne pageOne;
	public static HtmlCsRunner htmlCsRunner;
	public static AxeRunner axeRunner;


	/*@BeforeTest
	public void setUp() throws IOException {
		loadConfig();
	}
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		driver.get(properties.getProperty("baseURL"));
		 pageOne = new PageOne(driver);
	}*/
	@BeforeMethod
	@Parameters({"os" , "browser"})
	public void setUp(@Optional("") String os, @Optional("edge") String browser) throws IOException {
		loadConfig();
		timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		switch (browser.toLowerCase()) {
			case "chrome" ->{
				driver = new ChromeDriver();
				System.out.println("This test cases test on: " + browser);}
			case "firefox" ->{
				driver = new FirefoxDriver();
				System.out.println("This test cases test on: " + browser);}
			case "edge"->{
				driver = new EdgeDriver();
				System.out.println("This test cases test on: " + browser);}
			default -> System.out.println("Invalid browser");
		}
		htmlCsRunner = new HtmlCsRunner(driver);
		axeRunner = new AxeRunner(driver);
		driver.get(properties.getProperty("baseURL"));
		pageOne = new PageOne(driver);
	}

	@AfterMethod
	public void tearDown() throws IOException {
		//target/java-a11y/htmlcs/html
		htmlCsRunner.execute();  //To run accessibility tests
		htmlCsRunner.generateHtmlReport(); // To generate HTML report for accessibility tests


		// Wait for a key element to ensure the page is loaded (adjust selector as needed)
		/*new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
		axeRunner.execute();*/
		axeRunner.generateHtmlReport();
		driver.quit();
	}


		public void loadConfig() throws FileNotFoundException, IOException {
			try (FileInputStream file = new FileInputStream
					("src/Configuration/config.properties")) {

				properties = new Properties();
				properties.load(file);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}
	}

