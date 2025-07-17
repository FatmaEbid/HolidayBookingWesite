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
	public static String takeScreenShotReport(WebDriver driver,  String screenshotname) throws Exception{
		String dateName = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
		//WebElement element =driver.findElement(ele);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + "/Screenshots/";
		File destFile = new File(filePath+screenshotname+dateName+".png");
		FileUtils.copyFile(scrFile, destFile);
		return destFile.getAbsolutePath();//This allows the calling code to know exactly where the screenshot was saved on disk.
	}

	/*public static String takeScreenShotReportBase64(WebDriver driver) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			return ts.getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} */

}
