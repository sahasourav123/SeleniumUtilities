package example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utility.GetDriver;
import utility.ObjectRepository;

public class KnowElementExample {
	
	WebDriver driver;
	ObjectRepository know;

	@Before
	public void setUp() throws Exception {
		String url = "https://www.seleniumeasy.com/test/input-form-demo.html";
		driver = new GetDriver().chromeDriver(url);
		know = new ObjectRepository(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
		know.developRepository();
	}

}
