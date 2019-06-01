package example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pojo.DesiredException;
import pojo.Keywords;
import utility.CustomWait;
import utility.DriverSupport;
import utility.GetDriver;

public class Examples {

	WebDriver driver;
	DriverSupport support;
	CustomWait wait;
	
	@Before
	public void setUp() throws Exception {
		String url = "https://www.seleniumeasy.com/test/input-form-demo.html";
		driver = new GetDriver().chromeDriver(url);
		support = new DriverSupport(driver);
		wait = new CustomWait(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test_01() throws DesiredException {
		support.getElement(Keywords.Name, "first_name").sendKeys("Sourav");
		support.getElement(Keywords.NameContains, "last_name").sendKeys("Saha");
		support.getElement(Keywords.NameStartWith, "email").sendKeys("test.mail@gmail.com");
		support.getElement(Keywords.NameEndWith, "phone").sendKeys("9876543210");
		support.getElement(Keywords.Name, "address").sendKeys("Saha Maison");
		support.getElement(Keywords.Name, "city").sendKeys("Kolkata");
		support.getSelect(Keywords.Name, "state").selectByVisibleText("Texas");
		support.setText(Keywords.Name, "zip", "700093");
		support.getElement("nameStartWith", "website").sendKeys("https://github.com/sahasourav123");
		support.selectRadioOption(Keywords.Name, "hosting", "No");
		support.setText(Keywords.Name, "comment", "Selenium Utilities");
		support.ScreenShot();
		support.jsClick(Keywords.Xpath, "//button[@type='submit']");
		wait.waitForPageLoad();
	}
	
}
