package TestCases;

import Pages.PageOne;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
	public  WebDriver driver;
	public Properties properties;
	PageOne pageOne;

	@BeforeTest
	public void setUp() throws IOException {
		loadConfig();
	}
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
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

