package utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.DriverSupport.Locator;

public class CustomWait {
	WebDriver driver;
	DriverSupport support;
	CustomLogger logger;

	public CustomWait(WebDriver driver) {
		this.driver = driver;
		logger = new CustomLogger(Thread.currentThread().getStackTrace()[2].getClassName());
		support = new DriverSupport(driver);
	}

	public void waitForPageLoad() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver wdriver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			});
		} catch (Exception e) {
			staticWait(3);
		}
	}

	public void staticWait(int waitTime) {
		try {
			Thread.sleep(waitTime * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean explicitWait(int timeToWait, Locator howToIdentify, String elementId) {
		By locator = support.customLocator(howToIdentify, elementId);
		return explicitWait(timeToWait, locator);
	}

	public boolean explicitWait(int timeToWait, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWait);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean waitForElementWithinFrame(int timeToWait, Locator howToIdentify, String elementId, List<String> nestedFrames) {
		if(support.switchToFrameWithWait(nestedFrames, timeToWait)) {
			return explicitWait(timeToWait, howToIdentify, elementId);
		}
		return false;
	}

	// explicit wait for an element in frame
	public boolean waitForElementWithinWindow (int timeToWait, Locator howToIdentify, String elementId, String window) {
		if(support.switchToWindow(window, timeToWait)) {
			return explicitWait(timeToWait, howToIdentify, elementId);
		}
		return false;
	}

	public boolean staleWait(int timeToWait, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeToWait);
			wait.until(ExpectedConditions.stalenessOf(element));
			return true;
		} catch (Exception e) {
			logger.error("Element is not stolen");
			return false;
		}
	}

	public boolean enableWait(int timeToWait, Locator howToIdentify, String elementId) {
		By locator = support.customLocator(howToIdentify, elementId);
		return enableWait(timeToWait, locator);
	}

	public boolean enableWait(int timeToWait, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeToWait);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			logger.error("Disabled Element: " + locator);
			return false;
		}
	}

	public boolean waitForElementWithoutException(int timeToWait, Locator howToIdentify, String elementId) {
		try {
			explicitWait(timeToWait, howToIdentify, elementId);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean waitForStalenessOfElement(int timeToWait, Locator howToIdentify, String elementId) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWait);
		By locator = support.customLocator(howToIdentify, elementId);
		WebElement element = driver.findElement(locator);

		try {
			wait.until(ExpectedConditions.stalenessOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForInvisibilityOfElement(int timeToWait, Locator howToIdentify, String elementId) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWait);
		By locator = support.customLocator(howToIdentify, elementId);

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
