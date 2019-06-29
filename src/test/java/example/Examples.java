package example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pojo.DesiredException;
import utility.*;
import utility.DriverSupport.*;

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
		action.setText(Locator.Name, "first_name", "Sourav");
		action.setText(Locator.NameStartWith, "last_name", "Saha");
		action.setText(Locator.NameEndWith, "email", "test.mail@gmail.com");
		action.setText(Locator.NameContains, "phone", "9876543210");
		action.setText(Locator.Name, "address", "Saha Maison");
		action.setText(Locator.Name, "city", "Kolkata");
		action.selectDropdownOption(Locator.Name, "state", "Texas");
		action.setText(Locator.Name, "zip", "700093");
		action.setText(Locator.Name, "website", "https://github.com/sahasourav123");
		action.selectRadioOption(Locator.Name, "hosting", "No");
		action.setText(Locator.Name, "comment", "Selenium Utilities");
		support.takeScreenShot(ScreenShotType.FullPage);
		support.jsClick(Locator.Xpath, "//button[@type='submit']");
		wait.waitForPageLoad();
	}
	
}
