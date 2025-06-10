package Utilities;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Properties;


public class ExtentReports implements ITestListener {
	String repName;
	com.aventstack.extentreports.ExtentReports extentReports;
	ExtentSparkReporter sparkReporter;
	File file;
	ExtentTest test;


	public void onStart(ITestContext context) {
		String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		repName = "report " + dateFormat + ".html";

		file = new File(System.getProperty("user.dir") + "/resources/Reports/" + repName);
		extentReports = new com.aventstack.extentreports.ExtentReports();
		sparkReporter = new ExtentSparkReporter(file);
		extentReports.attachReporter(sparkReporter);

		//document name
		sparkReporter.config().setDocumentTitle("Report Title");
		// report name, it will displayed at the top left of the page
		sparkReporter.config().setReportName("Test Cases for Automation Exercises Website ");


		//Timestamp
		sparkReporter.config().setTimeStampFormat("HH:mm:ss dd-MM-yyyy");

		// report theme
		sparkReporter.config().setTheme(Theme.DARK);

		// to get browser Name and version by using webdriver and capabilities
		/*capabilities = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(capabilities.getBrowserName());   // to print the browser name
		System.out.println(capabilities.getBrowserVersion());  // to print the browser version*/

		// to get the java version
		extentReports.setSystemInfo("java version", System.getProperty("java.version"));
		//user Name
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));

		//String os = context.getCurrentXmlTest().getParameter("os");
		extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
		//extentReports.setSystemInfo("Host Name", System.getProperty("host.name"));

		String browser = context.getCurrentXmlTest().getParameter("browser");
		extentReports.setSystemInfo("Browser", browser);  // browser Name

		List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includeGroups.isEmpty()) {
			extentReports.setSystemInfo("Groups", includeGroups.toString());
		}

	}

	public void onFinish(ITestContext context) {
		extentReports.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + repName;
		File file = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
// Send the report via email
		sendEmailWithReport(file);
	}

	private void sendEmailWithReport(File reportFile) {
		// Email configuration
		String host = "smtp.example.com"; // Replace with your SMTP server
		String from = "fatma.ebid@nationaltrust.org.uk"; // Replace with your email
		String password = "your-email-password"; // Replace with your email password
		String to = "stakeholder@example.com"; // Replace with recipient email

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			// Create the email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Test Automation Report");
			message.setText("Please find the attached test automation report.");

			// Attach the report
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			messageBodyPart.attachFile(reportFile);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// Send the email
			Transport.send(message);
			System.out.println("Email sent successfully with the report.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to send email with the report.");
		}
	}
	public void onTestStart(ITestResult result) {
		//extentReports.createTest(result.getTestClass().getName()+ "-->"+result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		extentReports.createTest(result.getTestClass().getName()+ "--> "+result.getMethod().getMethodName())
				.log(Status.PASS, "<b>This test passed, log</b>")
				.log(Status.INFO, "this Info log")
				.assignCategory(result.getTestClass().getName()+ "--> "+result.getMethod().getMethodName())
				.assignAuthor(System.getProperty("user.name"))
				.assignDevice(System.getProperty("os.name"))

				//Using MarkUpHelper to highlight the text
				.info(MarkupHelper.createLabel(("For highlighted message"), ExtentColor.AMBER));
/*
		Throwable throwable = new RuntimeException("This is a custom exception");
		extentReports.createTest(result.getTestClass().getName())
				.fail(throwable);*/
		// Added the author, category, device for this test
		/*
		extentReports.createTest(result.getMethod().getMethodName())
				.assignAuthor(System.getProperty("user.name"))
				.assignCategory(result.getMethod().getMethodName());

		 */

	}

	public void onTestFailure(ITestResult result) {
		test = extentReports.createTest(result.getTestClass().getName()+ "-->"+result.getMethod().getMethodName())
				.info(result.getTestClass().getName()+ "-->"+result.getMethod().getMethodName());
		//test.fail(result.getThrowable().getMessage());
		test.log(Status.FAIL, "This Test failed");

	}

	public void onTestSkipped(ITestResult result) {
		extentReports.createTest(result.getTestClass().getName()+ "--> "+result.getMethod().getMethodName())
				.log(Status.SKIP, result.getTestClass().getName()+ "--> "+result.getMethod().getMethodName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		test.log(Status.FAIL, "Failed with percentage " + result.getTestClass().getName()+ "--> "+result.getMethod().getMethodName());
	}

}
