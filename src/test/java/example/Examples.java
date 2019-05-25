package example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import utility.CustomWait;
import utility.DesiredException;
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
	public void test() throws DesiredException {
		support.getElement("name", "first_name").sendKeys("Sourav");
		support.getElement("name", "last_name").sendKeys("Saha");
		support.getElement("name", "email").sendKeys("test.mail@gmail.com");
		support.getElement("name", "phone").sendKeys("9876543210");
		support.getElement("name", "address").sendKeys("Saha Maison");
		support.getElement("name", "city").sendKeys("Kolkata");
		support.getElement("name", "zip").sendKeys("700093");
		support.getElement("name", "website").sendKeys("https://github.com/sahasourav123");
		support.getElement("name", "comment").sendKeys("Selenium Utilities");
		support.ScreenShot();
		support.jsClick("xpath", "//button[@type='submit']");
		wait.waitForPageLoad();
	}

}
