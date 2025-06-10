package Utilities;

import com.deque.html.axecore.results.Results;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

import static org.testng.Assert.assertTrue;

public class AxeBuilder {
    Properties properties;
    WebDriver webDriver;

    public AxeBuilder() {
        properties = new Properties();
        properties.setProperty("baseURL", "http://localhost"); // Replace with actual URL or loading logic
        webDriver = new ChromeDriver();

        webDriver.get(properties.getProperty("baseURL"));

        try {
            Results axeResults = this.analyze(webDriver);
            assertTrue(axeResults.violationFree());
        } catch (RuntimeException e) {
            // Do something with the error
        }
    }

    public Results analyze(WebDriver driver) {
        // Implement the analyze logic or call the actual AxeBuilder analyze method
        return null;
    }
}

