package utility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class KnowElements {

	WebDriver driver;
	DriverSupport support;
	MyLogger logger;

	public KnowElements(WebDriver driver) {
		this.driver = driver;
		support = new DriverSupport(driver);
		logger = new MyLogger();
	}

	public void fetchBot() {
		while (true) {
			int n = JOptionPane.showConfirmDialog(null, "Yes: Capture\nNo: Terminate", "Capture Page Elements",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				logger.info("Title: " + driver.getTitle());
				logger.info("Url: " + driver.getCurrentUrl());
				fetchElements();
				support.ScreenShot();
				support.takeScreenShot();

			} else {
				logger.info("Terminate...");
				break;
			}
		}
	}

	public void fetchElements() {
		String[] skipTag = { "div", "section", "form" };
		List<String> skipTagList = Arrays.asList(skipTag);
		int counter = -1;
		List<WebElement> allElements = driver.findElements(By.xpath("//*[not(div)]"));
		for (WebElement element : allElements) {
			Properties prop = support.getExtrinsicProperty(element);
			String iLocation = prop.getProperty("iLocation");
			if (!iLocation.equals("0.0")) {
				Object iPropertyObject = support.getIntrinsictProperty(element);
				@SuppressWarnings("unchecked")
				Map<String, String> iProperty = (Map<String, String>) iPropertyObject;

				if (iProperty.size() > 0 && !skipTagList.contains(element.getTagName()))
					System.out.println(++counter + " : " + element.getTagName() + " : " + iLocation + "\t-> "
							+ iProperty + "\t-> " + element.getText());
			}
		}
	}
}
