package utility;

import org.openqa.selenium.WebDriver;

import pojo.DesiredException;
import pojo.Keywords;

public class DriverAction implements iDriverAction{

	WebDriver driver;
	DriverSupport support;
	MyLogger logger;
	
	public DriverAction(WebDriver driver) {
		this.driver = driver;
		this.support = new DriverSupport(driver);
		this.logger = new MyLogger(Thread.currentThread().getStackTrace()[2].getClassName() + " : " + this.getClass().getSimpleName());
	}
	
	@Override
	public boolean selectRadioOption(Keywords locatorType, String locatorValue, String optionValue) {
		String xpath = "//input[@" + locatorType.toString().toLowerCase() + "='" + locatorValue + "' and @value='" + optionValue.toLowerCase() + "']";
		try {
			support.jsClick(Keywords.Xpath, xpath);
			return true;
		} catch (Exception e) {
			logger.error("Not Found: xpath-> " + xpath);
			return false;
		}
	}
	
	@Override
	public boolean selectDropdownOption(Keywords locatorType, String locatorValue, String optionValue) {
		try {
			support.getSelect(locatorType, locatorValue).selectByVisibleText(optionValue);
			return true;
		} catch (DesiredException e) {
			logger.error("Not Found: " + locatorType + "-> " + locatorValue);
			return false;
		}
	}
	
	@Override
	public boolean setText(Keywords locatorType, String locatorValue, String text) {
		try {
			support.getElement(locatorType, locatorValue).sendKeys(text);
			return true;
		} catch (DesiredException e) {
			logger.error("Not Found: " + locatorType + "-> " + locatorValue);
			return false;
		}
	}

}
