package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import core.ExcelUtils;
import tests.TestBase;


public class SelectFlight extends TestBase{
	
	private WebDriver driver;


    public SelectFlight(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    

    @FindBy(xpath =".//label[@id='label_1_1_1']")
    private WebElement roundTrip;
    
    @FindBy(xpath =".//div[@class='omg-custom-passenger-val']")
    private WebElement passengers;
    
    @FindBy(xpath =".//div[@class='omg-custom-passenger-box-wrap']//div[1]//div[2]//span[2]")
    private WebElement adultsPlus;
    
    @FindBy(xpath =".//input[@id='input_1_4']")
    private WebElement fromField;
    
    @FindBy(xpath =".//li//div")
    private List<WebElement> cities;
    
    @FindBy(xpath =".//input[@id='input_1_3']")
    private WebElement toField;
    
    @FindBy(xpath =".//input[@id='input_1_5']")
    private WebElement departureFieldDate;
    
    @FindBy(xpath =".//select[@aria-label='Select month']")
    private WebElement DepartMonth;
    
    @FindBy(xpath =".//select[@aria-label='Select year']")
    private WebElement DepartYear;
    
    @FindBy(xpath =".//tbody/tr/td")
    private List<WebElement> days;
    
    @FindBy(xpath =".//input[@id='input_1_6']")
    private WebElement returnFieldDate;
    
    @FindBy(xpath =".//select[@aria-label='Select month']")
    private WebElement returnMonth;
    
    @FindBy(xpath =".//li[@id='menu-item-1277']//a[@class='menu-link'][normalize-space()='Blog']")
    private WebElement blog;
  
    
  
    public void blogSection() {
    	blog.click();
    }
    
    
    public String getWebPageTitle()  {return driver.getTitle();}
    
    
    public int flightChoice() throws InterruptedException, IOException {
    	String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\DummyFlightTicket.xlsx";
    	//select round trip
    	roundTrip.click();
        //select two adult passengers
    	passengers.click();
    	adultsPlus.click();
        //Click on 'From' field and select autosuggest dropdown FC
        fromField.sendKeys(ExcelUtils.getCellData(filePath,"flights",1,1));
        //List<WebElement> cities = driver.findElements(By.xpath("//li//div"));
        Thread.sleep(1000);
        String airportSelected = null;
        String airportSelectedDestination = null;
        System.out.println("Departure Airports suggested are: "+cities.size()+ " and displayed below:");
        // Display the airports and select FCO - Rome
        for(WebElement city:cities) {
        	System.out.println(city.getText());
        	if(city.getText().equals("FCO - Rome")) {
        		String[] air = city.getText().split(" ");
        		airportSelected = air[0];
        		city.click();
        	}
        	//System.out.println("------> "+airportSelected);
        }
        
      //Click on 'To' field and select autosuggest dropdown New
        toField.sendKeys(ExcelUtils.getCellData(filePath,"flights",1,2));
        Thread.sleep(2000);
        List<WebElement> airports = driver.findElements(By.xpath("//li//div"));
        System.out.println("Arrival Airports suggested are: "+airports.size()+ " and displayed below:");
        // Display the airports and select JFK - New York
        for(WebElement airport:airports) {
        	System.out.println(airport.getText());
        	if(airport.getText().equals("JFK - New York")) {
        		airport.click();
        		String[] air = airport.getText().split(" ");
        		airportSelectedDestination = air[0];
        	}
        }
        
       // select departure date 
        departureFieldDate.click();
        //WebElement DepartMonth = driver.findElement(By.xpath("//select[@aria-label='Select month']"));
        Select dm = new Select(DepartMonth);
        dm.selectByVisibleText(ExcelUtils.getCellData(filePath,"flights",1,4));
        //WebElement DepartYear = driver.findElement(By.xpath("//select[@aria-label='Select year']"));
        
        Select dy = new Select(DepartYear);
        dy.selectByVisibleText(ExcelUtils.getCellData(filePath,"flights",1,3));
        Thread.sleep(1000);
        //List<WebElement> days = driver.findElements(By.xpath("//tbody/tr/td"));
        for(WebElement day:days) {
        	if(day.getText().equals(ExcelUtils.getCellData(filePath,"flights",1,5))) {day.click();
        	break;}
        }
        
     // select return date 
        //driver.findElement(By.xpath("//input[@id='input_1_6']")).click();
        returnFieldDate.click();
        //WebElement returnMonth = driver.findElement(By.xpath("//select[@aria-label='Select month']"));
      
        Select rm = new Select(returnMonth);
        rm.selectByVisibleText(ExcelUtils.getCellData(filePath,"flights",1,7));
        
        WebElement ReturnYear = driver.findElement(By.xpath("//select[@aria-label='Select year']"));
        
        Select ry = new Select(ReturnYear);
        ry.selectByVisibleText(ExcelUtils.getCellData(filePath,"flights",1,6));
        
        List<WebElement> rdays = driver.findElements(By.xpath("//tbody/tr/td"));
        for(WebElement day:rdays) {
        	if(day.getText().equals(ExcelUtils.getCellData(filePath,"flights",1,8))) {day.click();
        	break;}
        }
        //click on 'search flights' button
        driver.findElement(By.xpath("//button[@id='omg-btn-search-flights']")).click();
        
        Thread.sleep(9000);
        
        List<WebElement> totalNumberFlights = driver.findElements(By.xpath("(//div[contains(text(),'"+airportSelected+"')])"));
        System.out.println("Total flight found from "+airportSelected+" to "+airportSelectedDestination+" are: " +totalNumberFlights.size());
        //Scroll down to display some flights:
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,3000)", "");     
        Thread.sleep(2000);
        
        //Flights Screenshot 
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir")+"/screenshots/flights"+formatDate()+".png");
        source.renameTo(target);
        return totalNumberFlights.size();
    }
    
    
  

}
