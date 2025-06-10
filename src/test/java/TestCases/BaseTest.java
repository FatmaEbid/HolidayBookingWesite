package TestCases;

import Pages.PageOne;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BaseTest {
	public  WebDriver driver;
	public Properties properties;
	String timestamp;
	PageOne pageOne;

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
	public void setUp(@Optional("") String os, @Optional("chrome") String browser) throws IOException {
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
		driver.get(properties.getProperty("baseURL"));
		pageOne = new PageOne(driver);
	}

	@AfterMethod
	public void tearDown() {
			driver.quit();
	}


		public void loadConfig() throws FileNotFoundException, IOException {
			try (FileInputStream file = new FileInputStream
					("src/Configration/config.properties")) {

				properties = new Properties();
				properties.load(file);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}
	}

