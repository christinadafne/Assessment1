package testcases;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import BaseClass.Baseclass_func;
import Pages.AddEmployee_Page;
import Pages.Contact_Page;
import Pages.Login_Page;
import Pages.PIM_Page;

public class test_cases extends Baseclass_func {

	@Test(groups = { "TC_Login_01" })
	public void login_validtest() throws Exception {
		test = extent.createTest("valid");
		lp.login(username, pass);
		validmsg();

	}

	@Test(groups = { "TC_Login_02" })
	public void login_invaliduname() throws Exception {
		pageobject();
		test = extent.createTest("invalid username");
		lp.login("invalid", pass);

		invalidmsg();

	}

	@Test(groups = { "TC_Login_02" })
	public void login_invalidpass() throws Exception {

		test = extent.createTest("invalid password");
		lp.login(username, "invalid");
		invalidmsg();

	}

	@Test(groups = { "TC_Login_02" })
	public void login_bothinvalid() throws Exception {

		test = extent.createTest("invalid");
		lp.login("invalid", "invalid");
		invalidmsg();

	}

	@Test(groups = { "TC_Login_02" })
	public void login_empty() throws Exception {

		test = extent.createTest("empty value");
		lp.login(" ", " ");
		emptymsg();

	}

	@Test(dataProvider = "details")
	public void PIM_addemp(String fname, String mname, String lname, String gender, String empid) throws Exception {

		test = extent.createTest("add employee");
		pp.PIM_addemp(fname, mname, lname, gender, empid);

	}

	@Test
	public void PIM_modify() throws Exception {

		test = extent.createTest("modify emloyee");
		pp.PIM_modify("171");

	}

	@Test
	public void PIM_delete() throws Exception {

		test = extent.createTest("delete employee");
		pp.PIM_delete("117");

	}

	@DataProvider(name = "details")
	public Object[][] userdetails() {
		return new Object[][] { { "Arun", "memp1", "lemp1", "male", "117" },
				{ "Anu", "memp2", "lemp2", "female", "171" } };
	}

}
