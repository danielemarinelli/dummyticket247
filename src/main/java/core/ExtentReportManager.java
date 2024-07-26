package core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;  // manage the report UI 
	public ExtentReports extent;  // populates common info on the report
	public ExtentTest test;  // creates test cases entries in the report and updates status of the test methods
	
	public void onStart(ITestContext context) {
		
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/flightsReport.html");  // report's location
		
		sparkReporter.config().setDocumentTitle("Automation Report");  // Title of the report
		sparkReporter.config().setReportName("Functional Testing");   //name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Computer Name", "Localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("os", "Windows10");
		extent.setSystemInfo("Tester", "Daniele Marinelli");
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getName());  //creates a new entry in the report
		test.log(Status.PASS, "Test case PASSED is: "+result.getName());  //updates status f/p/s
		
	}
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getName());  
		test.log(Status.FAIL, "Test case FAILED is: "+result.getName());  
		test.log(Status.FAIL, "Test case FAILED and cause is: "+result.getThrowable()); 
	}
	
	public void onTestSkip(ITestResult result) {
		
		test = extent.createTest(result.getName());  
		test.log(Status.SKIP, "Test case SKIPPED is: "+result.getName());  
		
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	
}
