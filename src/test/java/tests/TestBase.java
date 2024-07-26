package tests;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	private WebDriver driver;
	String date = null;
	
	
	public WebDriver driver() {
        return driver;
    }

	 	@BeforeClass
	    public void initDriver() {
		 	WebDriverManager.chromedriver().setup();
	        driver =  new ChromeDriver();
	        
	    }
	 	
	 	@BeforeSuite
	    public void createFolders()  {
	 		File reports = new File(System.getProperty("user.dir")+"/reports");
	        if(!reports.exists()){ reports.mkdir();}
	        File screen = new File(System.getProperty("user.dir")+"/screenshots");
	        if(!screen.exists()){ screen.mkdir();
	        File comments = new File(System.getProperty("user.dir")+"/blogs");
	        if(!comments.exists()) {comments.mkdir();}
	        }
	        
	    }
	 	
	 	@BeforeMethod
	    public void launchApp() {
	 		//driver.get(TestConfig.getProperty("appHomeURL"));
	 		driver.get("https://dummyticket247.com/");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    }
	 	
	 	
	 	    @AfterClass
	 	    public void cleanUp() {
	 	        if(driver!=null) {
	 	            driver.close();
	 	        }
	 	    }
	 	  
	 	/*public void scrollDownPage(WebDriver driver) {
	 		  JavascriptExecutor js = (JavascriptExecutor)driver;
	          js.executeScript("window.scrollBy(0,2000)", "");
	 	}*/
	 	    
	 	    @AfterMethod
	 	    public void takeScreenShotIfTestsFails(ITestResult result) throws Exception {
	 	        if (ITestResult.FAILURE == result.getStatus()) {
	 	            TakesScreenshot camera = ((TakesScreenshot) driver());
	 	            File screenShot = camera.getScreenshotAs(OutputType.FILE);
	 	            File DestFile = new File("./screenshots/"+result.getName()+"_Fail_"+formatDate()+".png");
	 	            FileHandler.copy(screenShot, DestFile);
	 	        }
	 	    }

	 	    public String formatDate(){
	 	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 	        LocalDateTime now = LocalDateTime.now();
	 	        date = dtf.format(now);
	 	        date = date.replaceAll("/","").replaceAll(":","").replaceAll(" ","");
	 	        return date;
	 	    }
	 	  

}
