package A11yUtil;

import freemarker.template.Template;
import io.github.sridharbandi.a11y.Engine;
import io.github.sridharbandi.ftl.FtlConfig;
import io.github.sridharbandi.modal.axe.Issues;
import io.github.sridharbandi.modal.htmlcs.Params;
import io.github.sridharbandi.util.A11y;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRunner {
	Object execute() throws IOException;

	static void generateHtmlReport(A11y a11y, Engine engine, Class<?> clazz) throws IOException {
		//The templates are fetched using the FtlConfig utility, which provides the template based on the engine name (e.g., axe or htmlcs).
		Template tmplIndex = FtlConfig.getInstance().getTemplate(engine.name().toLowerCase() + "/index.ftl");
		Template tmplPage = FtlConfig.getInstance().getTemplate(engine.name().toLowerCase() + "/page.ftl");//Used to generate individual pages for each issue.
		List<?> issuesList = a11y.jsonReports(engine, clazz);

		issuesList.forEach(issues -> {
			if (issues == null) {
				System.out.println("Skipping null issue in issuesList");
				return; // Skip null entries
			}
			//String id = engine.name().equalsIgnoreCase("axe") ? ((Issues) issues).getId() : ((io.github.sridharbandi.modal.htmlcs.Issues) issues).getId();
			//String reportName = engine.name().toUpperCase() + "_" + clazz.getSimpleName() + "_Page_"  + "_" + new Date().getTime();
String reportName = engine.name().toUpperCase() + "_" + "_Page_";
			a11y.save(tmplPage, issues, reportName, engine);
		});

		Map<String, Object> root = new HashMap<>();
//List<?> issuesList = a11y.execute(engine, new io.github.sridharbandi.modal.htmlcs.Params());		root.put("list", issuesList);

		// Generate a meaningful report name for index.html file
		root.put("title", "Accessibility Report: " + engine.name().toUpperCase()+"_"
				+ clazz.getSimpleName() + "_" + new Date().toString());
		a11y.save(tmplIndex, root, "index", engine);
	}
}
