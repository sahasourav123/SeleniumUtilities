package utility;

import org.openqa.selenium.WebDriver;

import pojo.DesiredException;

public class WebPageHandle {

	WebDriver driver;
	
	public boolean validateEnvironment() throws DesiredException {
		String pageTitle = driver.getTitle();
		if (pageTitle.contains("This page can’t be displayed") || pageTitle.contains("Forbidden")
				|| pageTitle.contains("Error") || pageTitle.contains("Bad Gateway")
				|| pageTitle.contains("Not Found")) {
			throw new DesiredException("Environment Unavailable : " + pageTitle);
		}
		return true;
	}

	public void validateLogin() throws DesiredException {
		String pageTitle = driver.getTitle();
		if (pageTitle.contains("attempt to login has been unsuccessful")) {
			throw new DesiredException("Incorrect UserId/Password");

		} else {
			validateEnvironment();
		}
	}
}
