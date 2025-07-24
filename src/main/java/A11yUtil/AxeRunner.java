package A11yUtil;

import io.github.sridharbandi.a11y.AxeTag;
import io.github.sridharbandi.a11y.Engine;
import io.github.sridharbandi.modal.htmlcs.Issues;
import io.github.sridharbandi.modal.htmlcs.Params;
import io.github.sridharbandi.util.A11y;
import org.openqa.selenium.WebDriver;
import A11yUtil.IRunner;

import java.io.IOException;
/**
 * AxeRunner is a utility class for running accessibility tests using the Axe engine.
 * It allows setting various parameters such as page title, tags, rules, and script URL.
 * The execute method runs the accessibility tests and returns the issues found.
 */

public class AxeRunner implements A11yUtil.IRunner {
	private A11y a11y;
	private Params params;

	public AxeRunner(WebDriver driver) {
		a11y = new A11y(driver);
		params = new Params();
	}

	public AxeRunner setPageTile(String pageTitle) {
		params.setPageTitle(pageTitle);
		System.out.println("Page Title: "+params.getPageTitle());
		return this;
	}

	public AxeRunner setTags(AxeTag... tag) {
		params.setTags(tag);
		return this;
	}

	public AxeRunner disableRules(String... rules) {
		params.disableRules(rules);
		return this;
	}

	public AxeRunner enableRules(String... rules) {
		params.enableRules(rules);
		return this;
	}

	public AxeRunner setScriptURL(String url) {
		params.setScriptURL(url);
		return this;
	}

	@Override
	public Issues execute() throws IOException {
		// Log individual fields of the Params object
		System.out.println("Script URL: " + params.getScriptURL());
		System.out.println("Page Title: " + params.getPageTitle());
		System.out.println(params.getTags());
		System.out.println(params.getRules());



		// Ensure all required fields are set
		if (params.getScriptURL() == null || params.getPageTitle() == null) {
			throw new IllegalArgumentException("Script URL or Page Title is missing in Params.");
		}
		return (Issues) a11y.execute(Engine.AXE, params);
	}

	public void generateAXEReport() throws IOException {
		A11yUtil.IRunner.generateHtmlReport(new A11y(), Engine.AXE, Issues.class);
	}
}
