package Pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import utility.Utility_Class;

public class Contact_Page extends Utility_Class {

	@FindBy(xpath = "//a[text()='Contact Details']")
	WebElement contact;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[1]")
	WebElement street1;

	@FindBy(xpath = "//label[text()='Street 2']//following::input[1]")
	WebElement street2;

	@FindBy(xpath = "//label[text()='City']//following::input[1]")
	WebElement city;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[4]")
	WebElement state;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[5]")
	WebElement zip;

	@FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--active']")
	WebElement select;

	@FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--focus']//div[contains(text(),'Albania')]")
	WebElement country;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[6]")
	WebElement home_no;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[7]")
	WebElement mobile;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[8]")
	WebElement work_no;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[9]")
	WebElement work_email;

	@FindBy(xpath = "//span[text()='Already exists']")
	WebElement exist;

	@FindBy(xpath = "//input[@class='oxd-input oxd-input--active oxd-input--error'][1]")
	WebElement errormail1;

	@FindBy(xpath = "(//input[@class='oxd-input oxd-input--active oxd-input--error'])[2]")
	WebElement errormail2;

	@FindBy(xpath = "//label[text()='Street 1']//following::input[10]")
	WebElement other_email;

	@FindBy(xpath = "//input[@type='checkbox']")
	WebElement checkbox;

	@FindBy(xpath = "//button[text()=' Save ']")
	WebElement save;

	public Contact_Page() {
		PageFactory.initElements(driver, this);
	}

	public void address() throws Exception {
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		// Thread.sleep(5000);
		Expwait(contact);
		click_button(contact, "contact");

		Thread.sleep(2000);
		type_input(street1, "north");

		type_input(street2, "south street");
		type_input(city, "wallcity");
		type_input(state, "tamilnadu");
		type_input(zip, "1234");
		select.click();

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_I);
		WebElement trial = driver.findElement(By.xpath("//div[@class='oxd-select-wrapper']//child::div"));
		while (trial.getText().equalsIgnoreCase("India") == false)

		{
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);
		}
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		type_input(home_no, "12345");
		type_input(mobile, "98421");
		type_input(work_no, "67890");
		type_input(work_email, "testemp1@gmail.com");
		exist_msg(exist, errormail1, "abc11@gmail.com");
		type_input(other_email, "testemp2@gmail.com");
		exist_msg(exist, errormail2, "abc1123@gmail.com");

		click_button(save, "save");

		test.log(Status.PASS, "details added");
		test.addScreenCaptureFromPath(screenshot("details added"));
		// screenshot("details");
		// driver.close();

	}

	public WebElement getWork_email() {
		return work_email;
	}

	public WebElement getOther_email() {
		return other_email;
	}

}
