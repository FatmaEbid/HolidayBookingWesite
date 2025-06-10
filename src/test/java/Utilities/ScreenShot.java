package Utilities;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {
	WebDriver driver;
	public static void takeScreenShot(WebDriver driver,  String screenshotname) throws Exception{
		String dateName = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
		//WebElement element =driver.findElement(ele);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + "/Screenshots/";
		FileUtils.copyFile(scrFile, new File(filePath+screenshotname+dateName+".png"));
	}
	public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm").format(new Date());
		String filePath = System.getProperty("user.dir") + "/Screenshots/";
		//File screenshot = driver.getFullPageScreenshotAs(OutputType.FILE)

		Screenshot screenshot = new AShot()
				.shootingStrategy(ShootingStrategies.viewportPasting(100)) // Captures the entire page
				.takeScreenshot(driver);

		ImageIO.write(screenshot.getImage(), "PNG", new File(filePath + screenshotName + dateName + ".png"));
	}

}
