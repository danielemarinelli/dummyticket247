package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Blog;
import pages.SelectFlight;

public class test_DummyFlightTickets extends TestBase{
	
	SelectFlight webpage = null;
	Blog blog = null;
	
	@Test(priority = 1)
	public void test_correctWebpage() {
		        
        webpage = new SelectFlight(driver());
        String title = webpage.getWebPageTitle();
        Assert.assertEquals(title,"Dummy Flight Ticket","Wrong webpage");
        
      }
	
	@Test(priority = 2, dependsOnMethods = { "test_correctWebpage" })
	public void test_pickFlight() throws Exception {
		webpage = new SelectFlight(driver());
		int totalFlights = webpage.flightChoice();
		Assert.assertTrue(totalFlights>0);
	}
	
	@Test(priority = 3)
	public void test_blogPage() {
		webpage = new SelectFlight(driver());
		webpage.blogSection();
		blog = new Blog(driver());
		String url = blog.displayURL();
		Assert.assertEquals(url,"https://dummyticket247.com/blog/","Navigated to wrong URL");
	}
	
	@Test(priority = 4, dependsOnMethods = { "test_blogPage" })
	public void test_comments() throws IOException {
		webpage = new SelectFlight(driver());
		blog = new Blog(driver());
		webpage.blogSection();
		int totalNumberOfComments = blog.total_comments();
		//System.out.println(totalNumberOfComments);
		blog.saveCommentsToFile();
		Assert.assertTrue(totalNumberOfComments>0);
	}
	
	
	
	

}
