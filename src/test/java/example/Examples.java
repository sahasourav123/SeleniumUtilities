package example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pojo.DesiredException;
import pojo.Keywords;
import utility.CustomWait;
import utility.DriverAction;
import utility.DriverSupport;
import utility.GetDriver;

public class Examples {

	WebDriver driver;
	DriverSupport support;
	DriverAction action;
	CustomWait wait;
	
	@Before
	public void setUp() throws Exception {
		String url = "https://www.seleniumeasy.com/test/input-form-demo.html";
		driver = new GetDriver().chromeDriver(url);
		support = new DriverSupport(driver);
		wait = new CustomWait(driver);
		action = new DriverAction(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test_01() throws DesiredException {
		action.setText(Keywords.Name, "first_name", "Sourav");
		action.setText(Keywords.NameStartWith, "last_name", "Saha");
		action.setText(Keywords.NameEndWith, "email", "test.mail@gmail.com");
		action.setText(Keywords.NameContains, "phone", "9876543210");
		action.setText(Keywords.Name, "address", "Saha Maison");
		action.setText(Keywords.Name, "city", "Kolkata");
		action.selectDropdownOption(Keywords.Name, "state", "Texas");
		action.setText(Keywords.Name, "zip", "700093");
		action.setText(Keywords.Name, "website", "https://github.com/sahasourav123");
		action.selectRadioOption(Keywords.Name, "hosting", "No");
		action.setText(Keywords.Name, "comment", "Selenium Utilities");
		support.ScreenShot();
		support.jsClick(Keywords.Xpath, "//button[@type='submit']");
		wait.waitForPageLoad();
	}
	
}
