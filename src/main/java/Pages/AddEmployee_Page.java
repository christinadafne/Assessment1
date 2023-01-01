package Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utility.Utility_Class;

public class AddEmployee_Page extends Utility_Class {
	@FindBy(name="firstName")
	WebElement firstname;
	
	@FindBy(name="middleName")
	WebElement middlename;
	
	@FindBy(name="lastName")
	WebElement lastname;
	
	@FindBy(xpath="(//input[@class='oxd-input oxd-input--active'])[2]")
	WebElement emp_id;
	
	@FindBy(xpath="//input[@class='oxd-input oxd-input--active oxd-input--error']")
	WebElement errorid;
	
	

	@FindBy(xpath="//span[text()='Employee Id already exists']")
	WebElement dupid;
	
	@FindBy(xpath="//button[text()=' Cancel ']")
	WebElement cancel;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement save;
	
	@FindBy(xpath="//i[@class='oxd-icon bi-plus']")
	WebElement image;
	
	public AddEmployee_Page() {
		PageFactory.initElements(driver, this);
		
	}
	
	public void addemp(String fname,String mname,String lname,String gender,String empid) throws Exception {
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		type_input(firstname,fname);
		type_input(middlename, mname);
		type_input(lastname, lname);
		String gen=prop.getProperty(gender);
		//System.out.println("gender"+gen);
		String photo_loc=homedir+gen+"\\"+fname+".jpeg";
		//System.out.println("photo"+ photo_loc);
		chooseimage(image,photo_loc,empid);
		//clearingid(emp_id,dupid,empid);
	}
			
	

	public WebElement getEmp_id() {
		return emp_id;
	}

	public WebElement getDupid() {
		return dupid;
	}

	public WebElement getCancel() {
		return cancel;
	}

	public WebElement getSave() {
		return save;
	}
	public WebElement getErrorid() {
		return errorid;
	}
	
	

}
