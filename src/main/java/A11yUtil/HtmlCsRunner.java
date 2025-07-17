package A11yUtil;

import io.github.sridharbandi.IRunner;
import io.github.sridharbandi.a11y.Engine;
import io.github.sridharbandi.a11y.HTMLCS;
import io.github.sridharbandi.modal.htmlcs.Issues;
import io.github.sridharbandi.modal.htmlcs.Params;
import io.github.sridharbandi.util.A11y;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

public class HtmlCsRunner implements IRunner {
	private A11y a11y;
	private HTMLCS standard;
	private Params params;
	private String[] codes = {};
	private WebDriver driver;
	private Issues issues;

	public HtmlCsRunner(WebDriver driver) {
		a11y = new A11y(driver);
		params = new Params();
	}

	public HtmlCsRunner setStandard(HTMLCS standard) {
		this.standard = standard;
		return this;
	}

	public HtmlCsRunner setIgnoreCodes(String[] codes) {
		this.codes = codes;
		return this;
	}

	public HtmlCsRunner setPageTile(String pageTitle) {
		params.setPageTitle(pageTitle);
		return this;
	}

	public HtmlCsRunner setScriptURL(String url) {
		params.setScriptURL(url);
		return this;
	}
	public void captureScreenshot(WebDriver driver, String fileName) throws IOException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(screenshot.toPath(), Paths.get("Screenshots/" + fileName + ".png"));
	}
	public HtmlCsRunner setIssues(Issues issues) {
		this.issues = issues;
		return this;
	}

	@Override
	public Issues execute() throws IOException {
		String stdrd = Objects.isNull(standard) ? HTMLCS.WCAG2AA.name() : standard.name();
		params.setStandard(stdrd);
		params.setIgnoreCodes(codes);
		 Issues issues= (Issues) a11y.execute(Engine.HTMLCS, params);
	//	 captureScreenshot("HTMLCS_Report_" +new Date().getTime());
		 return issues;
	}

	public static void generateHtmlReport() throws IOException {
	A11yUtil.IRunner.generateHtmlReport(new A11y(), Engine.HTMLCS, Issues.class);
			//.generateHtmlReport(a11y, Engine.HTMLCS, Issues.class);
		//IRunner.super.generateHtmlReport(a11y, Engine.HTMLCS, Issues.class);
	}
}
