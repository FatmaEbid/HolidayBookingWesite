package Utilities.RetryAnalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Analyzer implements IRetryAnalyzer {
	int counter = 0;
	int limit = 3;
	@Override
	public boolean retry(ITestResult iTestResult) {
		if (counter<limit) {
			counter++;
			return true;
		}
		return false;
	}
}
