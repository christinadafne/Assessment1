package Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import utility.Utility_Class;


public class Login_Page extends Utility_Class{
	
	
@FindBy(name="username")
WebElement uname;

@FindBy(name="password")
WebElement password;



@FindBy(xpath="//button[@type='submit']")
WebElement loginbutton;

@FindBy(xpath="//h6[text()='Dashboard']")
WebElement dashboard;

@FindBy(xpath="//p[text()='Invalid credentials']")
WebElement errormsg;

@FindBy(xpath="//input[@name='username']//following::span")
WebElement empty;

public WebElement getEmpty() {
	return empty;
}

public WebElement getDashboard() {
	return dashboard;
}

public WebElement getErrormsg() {
	return errormsg;
}

public Login_Page() {
	PageFactory.initElements(driver, this); 
}

public void login(String name,String passw) throws Exception 
{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
		type_input(uname,name);
		
		//System.out.println("udata" +udata);
		type_input(password,passw);
		//System.out.println("pdata" +pdata);
		click_button(loginbutton,"login");
		//validmsg();
		
		
		
	
	
	
}

}
