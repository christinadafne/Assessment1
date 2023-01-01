package Pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import utility.Utility_Class;

import org.openqa.selenium.WebElement;

public class PIM_Page extends Utility_Class {
	public static String id_chk;

	@FindBy(linkText = "PIM")
	WebElement PIM;

	@FindBy(linkText = "Add Employee")
	WebElement addemp;

	@FindBy(xpath = "//h6[text()='Add Employee']")
	WebElement addemppage;

	@FindBy(xpath = "//a[text()='Employee List']")
	WebElement emplist;

	@FindBy(xpath = "//label[text()='Employee Id']//following::input[1]")
	WebElement enterid;

	@FindBy(xpath = "//div[text()='Current Employees Only']")
	WebElement include;

	@FindBy(xpath = "(//div[@class='oxd-select-wrapper'])[2]//div//div[text()='Current and Past Employees']")
	WebElement option;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement search;

	@FindBy(xpath = "//span[text()='No Records Found']")
	WebElement noRecord;

	@FindBy(xpath = "//div[@class='oxd-table-body']//span")
	WebElement recordtick;

	@FindBy(xpath = "//i[@class='oxd-icon bi-pencil-fill']")
	WebElement edit;

	@FindBy(xpath = "//*[text()='Job']")
	WebElement job;

	@FindBy(xpath="//label[text()='Job Specification']//preceding::div[1]")
	WebElement joboption;
	
	@FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--active']")
	WebElement select;

	@FindBy(xpath = "//i[@class='oxd-icon bi-check oxd-checkbox-input-icon']")
	WebElement checkbox;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement save;

	@FindBy(xpath = "//i[@class='oxd-icon bi-trash']")
	WebElement bin;

	@FindBy(xpath = "//button[text()=' Yes, Delete ']")
	WebElement confirm;

	public PIM_Page() {
		PageFactory.initElements(driver, this);
	}

	public void PIM_addemp(String fname, String mname, String lname, String gender, String empid) throws Exception {
		id_chk = empid;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		lp.login(username, pass);
		// Thread.sleep(2000);
		click_button(PIM, "PIM");
		click_button(addemp, "ADD EMPLOYEE");
		Assert.assertTrue(addemppage.isDisplayed());
		// screenshot("addemployee");
		test.log(Status.PASS, "add employee page loaded");
		test.addScreenCaptureFromPath(screenshot("add employee page"));
		ep.addemp(fname, mname, lname, gender, empid);
		// cp.address();

	}

	public void PIM_modify(String id_chk1) throws Exception {

		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		lp.login(username, pass);
		// Thread.sleep(2000);
		click_button(PIM, "PIM");
		click_button(emplist, "EMPLOYEE LIST");
		type_input(enterid, id_chk1);
		click_button(include, "include");
		WebElement include_text = driver.findElement(
				By.xpath("(//div[@class='oxd-select-wrapper']//child::div[@class='oxd-select-text-input'])[2]"));
		Robot robot = new Robot();
		while (include_text.getText().equalsIgnoreCase("Current and Past Employees") == false) {
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);
		}
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		click_button(search, "SEARCH");
		//noRecord(noRecord);
		try {
			
			Assert.assertTrue(noRecord.getText().equals("No Records Found"));
			test.log(Status.PASS, "no record found");
			test.addScreenCaptureFromPath(screenshot("norecord"), "norecord");
		} catch (Exception e) {
			Robot robot1 = new Robot();
			click_button(pp.getRecordtick(), "record ticked");
			click_button(pp.getEdit(), "edit option");
			
			click_button(pp.getJob(), "job");
			Thread.sleep(2000);
			click_button(pp.getJoboption(), "select job");

			robot1.keyPress(KeyEvent.VK_Q);
			WebElement trial = driver.findElement(By.xpath("//div[@class='oxd-select-wrapper']//child::div"));
			if (trial.getText().equalsIgnoreCase("QA Engineer"))
			{
				robot1.keyPress(KeyEvent.VK_ENTER);
				robot1.keyRelease(KeyEvent.VK_ENTER);
			} 
			else 
			{
				while (trial.getText().equalsIgnoreCase("QA Engineer") == false)

				{
					robot1.delay(300);
					robot1.keyPress(KeyEvent.VK_DOWN);
				}
				robot1.keyRelease(KeyEvent.VK_DOWN);
				robot1.keyPress(KeyEvent.VK_ENTER);
				robot1.keyRelease(KeyEvent.VK_ENTER);
				// Expwait(pp.getSave());
				click_button(pp.getSave(), "save");
				test.log(Status.INFO, trial.getText() + " modified");
			}
			
			
			test.log(Status.PASS, "employee job modified ");
			test.addScreenCaptureFromPath(screenshot("modified"));

		}
	}

	public WebElement getEdit() {
		return edit;
	}

	public WebElement getRecordtick() {
		return recordtick;
	}

	public WebElement getSave() {
		return save;
	}

	public WebElement getJob() {
		return job;
	}
	

	public WebElement getJoboption() {
		return joboption;
	}

	public void PIM_delete(String id_chk1) throws Exception {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		lp.login(username, pass);
		// Thread.sleep(2000);
		click_button(PIM, "PIM");
		click_button(emplist, "EMPLOYEE");
		type_input(enterid, id_chk1);
		click_button(search, "SEARCH");
		click_button(checkbox, "CHECBOX");
		click_button(bin, "DELETE");

		click_button(confirm, "CONFIRM");
		// screenshot("deleted")
		test.log(Status.PASS, "employee deleted");
		test.addScreenCaptureFromPath(screenshot("deleted"));

	}

}
