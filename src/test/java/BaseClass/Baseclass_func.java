package BaseClass;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utility.Utility_Class;

public class Baseclass_func extends Utility_Class
{
	
	
	
	@BeforeSuite
	public void prelaunch() throws Exception {
		property_file();
		photosproperty();
		report();
		 
	}
	@AfterSuite
	public void postlaunch() {
		
extent.flush();
	}
	
	@BeforeMethod
	public void launch() throws Exception {
		
		browser_launch(browser);
		pageobject();
		driver.manage().window().maximize();
			
	}
	
	
	  @AfterMethod
	  public void close()
	   { 
		  extent.flush();
	  driver.close(); 
	  
	  }
	 
	 

	}
	


