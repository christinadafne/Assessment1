package utility;

import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Pages.AddEmployee_Page;
import Pages.Contact_Page;
import Pages.Login_Page;
import Pages.PIM_Page;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.coobird.thumbnailator.Thumbnails;

public class Utility_Class

{
	public static WebDriver driver;
	public static String homedir;
	public static Properties prop;
	public static String folder, username, pass, browser, url;
	public static String male, female;
	public static Login_Page lp;
	public static PIM_Page pp;
	public static AddEmployee_Page ep;
	public static Contact_Page cp;
	public static Actions act;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentSparkReporter spark;
	public static ITestResult result;
	public static int count = 0;
	public static WebDriverWait wait;

	// driver module

	public WebDriver browser_launch(String brows) {
		WebDriverManager.chromedriver().setup();
		switch (brows) {
		case "chrome":
			driver = new ChromeDriver();
			driver.get(url);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			driver.get(url);
			break;
		case "safari":
			driver = new SafariDriver();
			driver.get(url);
			break;
		default:
			driver = new ChromeDriver();
			driver.get(url);
			break;
		}

		return driver;

	}

	// property file module

	public void property_file() throws Exception {
		prop = new Properties();
		homedir = System.getProperty("user.dir");
		// System.out.println(homedir);
		String path = homedir + "\\configuration.properties";
		// System.out.println(path);
		FileInputStream fin = new FileInputStream(path);

		prop.load(fin);

		url = prop.getProperty("url");
		username = prop.getProperty("username");
		pass = prop.getProperty("password");
		browser = prop.getProperty("browser");
		folder = prop.getProperty("folder");

	}

	// photos property module

	public void photosproperty() throws Exception {
		prop = new Properties();
		String path = homedir + "\\photos.properties";
		FileInputStream imgfile = new FileInputStream(path);
		prop.load(imgfile);
		male = prop.getProperty("male");
		female = prop.getProperty("female");

	}

	public void homedir() {
		homedir = System.getProperty("user.dir");
	}

	// class object module

	public void pageobject() {
		lp = new Login_Page();
		pp = new PIM_Page();
		ep = new AddEmployee_Page();
		cp = new Contact_Page();
		act = new Actions(driver);

	}

	// invalid credentials module

	public void invalidmsg() throws Exception {
		Thread.sleep(5000);

		if (lp.getErrormsg().isDisplayed() == true) {
			String msg = lp.getErrormsg().getText();
			test.log(Status.FAIL, msg);
			test.addScreenCaptureFromPath(screenshot("login failed"));

		}

	}

	// valid credentials module

	public void validmsg() throws Exception {
		if (lp.getDashboard().isDisplayed() == true) {
			test.log(Status.PASS, "The user is logged in successfully");
			// screenshot("login");
			// System.out.println("The user is logged in successfully");
			test.addScreenCaptureFromPath(screenshot("login successful"));

		}

	}

	// empty credentials module

	public void emptymsg() throws Exception {
		test.log(Status.FAIL, lp.getEmpty().getText());
		test.addScreenCaptureFromPath(screenshot("empty values"));

	}

	// image choosing module
	public void chooseimage(WebElement element, String path, String empid) throws Exception {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		element.click();
		StringSelection strSelection = new StringSelection(path);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(strSelection, null);

		Robot robot = new Robot();

		robot.delay(300);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_ENTER);

		test.log(Status.PASS, "image added");
		clearingid(ep.getEmp_id(), ep.getDupid(), empid);
	}

	// clearing id textbox module
	// re-enters for existing id
	public void clearingid(WebElement element, WebElement msg_element, String value) throws Exception {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		act = new Actions(driver);
		Expwait(element);
		act.doubleClick(element).build().perform();
		act.sendKeys(value).build().perform();
		test.log(Status.INFO, "Employee Id" + value + " entered");
		try {

			String errmsg = msg_element.getText();
			Assert.assertTrue(errmsg.equals("Employee Id already exists"));

			test.log(Status.INFO, "Employee Id already exists");
			count++;
			value = value + count;
			// System.out.println(value);
			clearingid(ep.getErrorid(), msg_element, value);

			test.log(Status.PASS, "employee id entered");
			test.addScreenCaptureFromPath(screenshot("ID entered"));
			Thread.sleep(2000);
			ep.getSave().click();
			test.log(Status.PASS, "save clicked");
			System.out.println("save clicked");
			cp.address();
		} catch (Exception e) {
			Expwait(ep.getSave());
			ep.getSave().click();
			test.log(Status.PASS, "save clicked");
			test.log(Status.PASS, "employee id" + value + " entered");
			test.addScreenCaptureFromPath(screenshot("ID entered"));

			cp.address();
		}
	}

	// existing mail id check module
	public void exist_msg(WebElement element, WebElement element1, String value) {
		try {
			// Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			Assert.assertTrue(element.getText().equals("Already exists"));
			// act.doubleClick(element1).build().perform();
			element1.clear();
			act.sendKeys(value).build().perform();
			// clearing(element1,element, value);
			test.log(Status.PASS, "email entered");

		} catch (Exception e) {
			test.log(Status.PASS, "email entered");
		}
	}

	// no record module
	public void noRecord(WebElement element) throws Exception {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		try {
			// driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			Assert.assertTrue(element.getText().equals("No Records Found"));
			test.log(Status.PASS, "no record found");
			test.addScreenCaptureFromPath(screenshot("norecord"), "norecord");
		} catch (Exception e) {
			Robot robot1 = new Robot();
			click_button(pp.getRecordtick(), "record ticked");
			click_button(pp.getEdit(), "edit option");
			click_button(pp.getJob(), "job");
			click_button(pp.getJoboption(), "select job");

			robot1.keyPress(KeyEvent.VK_Q);
			WebElement trial = driver.findElement(By.xpath("//div[@class='oxd-select-wrapper']//child::div"));
			if (trial.getText().equalsIgnoreCase("QA Engineer")) {
				robot1.keyPress(KeyEvent.VK_ENTER);
				robot1.keyRelease(KeyEvent.VK_ENTER);
			} else {
				while (trial.getText().equalsIgnoreCase("QA Engineer") == false)

				{
					robot1.delay(300);
					robot1.keyPress(KeyEvent.VK_DOWN);
				}
				robot1.keyRelease(KeyEvent.VK_DOWN);
				robot1.keyPress(KeyEvent.VK_ENTER);
				robot1.keyRelease(KeyEvent.VK_ENTER);
				// Expwait(pp.getSave());
			}
			click_button(pp.getSave(), "save");
			test.log(Status.INFO, trial.getText() + " modified");
			test.log(Status.PASS, "employee job modified ");
			test.addScreenCaptureFromPath(screenshot("modified"));

		}

	}

	// textbox input module

	public void type_input(WebElement element, String text) {
		try {
			// Assert.assertTrue(element.isDisplayed());
			element.sendKeys(text);
			test.log(Status.PASS, text + " Entered in the textbox");
		} catch (Exception e) {
			WebDriverWait wait = new WebDriverWait(driver, 20);//
			wait.until(ExpectedConditions.visibilityOf(element));
			type_input(element, text);
		}

	}

	// button click module

	public void click_button(WebElement element, String id) {
		try {
			element.click();
			test.log(Status.PASS, id + " button  clicked");
		} catch (Exception e) {
			test.log(Status.FAIL, id + " button not clicked");
		}

	}

	// screenshot module

	public String screenshot(String text) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(homedir + "/Screenshots/" + text + ".png");
		String path = homedir + "/Screenshots/" + text + ".png";
		// System.out.println(dest);
		FileUtils.copyFile(src, dest);
		return path;

	}
	// explicit wait module

	public void Expwait(WebElement element) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// extent report module

	public void report() {
		spark = new ExtentSparkReporter("ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);

	}

}
