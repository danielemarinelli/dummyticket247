package pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import tests.TestBase;

public class Blog{
	
	private WebDriver driver;
	File commentsfile = new File(System.getProperty("user.dir")+"/blogs/CommentsIntoBlogSection.txt");
	List<String> allComments = new ArrayList<String>();

    public Blog(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
       
  
    @FindBy(xpath =".//h2[@class='entry-title']")
    private List<WebElement> comments;
    
    public int total_comments() {
    	return comments.size();
    }
    
    
    public void saveCommentsToFile() throws IOException {
    	for(WebElement comm:comments) {
    		String c = comm.getText();
    		System.out.println(c);
    		System.out.println("------------------");
    		allComments.add(c);
    		
    	}
    	writeCommentsIntoFile(allComments);
    }
    
    
    public String displayURL() {
    	System.out.println(driver.getCurrentUrl());
    	return driver.getCurrentUrl();
    }
    
    public void writeCommentsIntoFile(List<String> com) throws IOException {
    	PrintWriter pw = new PrintWriter(commentsfile);
		pw.println(com);
		pw.flush();
    	//FileOutputStream fos = new FileOutputStream(commentsfile);
		//String str = com+"\n";
		//fos.write(str.getBytes());
    }
    
  

}
