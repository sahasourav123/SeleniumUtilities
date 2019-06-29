package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import toolkit.ExcelData;
import toolkit.SupportUtil;
import utility.DriverSupport.ScreenShotType;

public class KnowElements {

	WebDriver driver;
	DriverSupport support;
	CustomLogger logger;
	SupportUtil util;
	ArrayList<ArrayList<Object>> objectTable;

	public KnowElements(WebDriver driver) {
		this.driver = driver;
		support = new DriverSupport(driver);
		logger = new CustomLogger();
		util = new SupportUtil();
	}

	public void fetchBot() {
		objectTable = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> header = new ArrayList<Object>();
		header.add("Element Counter");
		header.add("Tag Name");
		header.add("Page Name");
		header.add("X-Cordinate");
		header.add("Y-Cordinate");
		header.add("Locators");
		header.add("Text");
		objectTable.add(header);
		
		logger.info("Fetch element bot initiated.");
		while (true) {
			int n = JOptionPane.showConfirmDialog(null, "Yes: Capture\nNo: Terminate", "Capture Page Elements",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				String pageName = driver.getTitle();
				logger.info("Title: " + pageName);
				logger.info("Url: " + driver.getCurrentUrl());
				fetchElements(pageName);
				support.takeScreenShot(ScreenShotType.FullPage);

			} else {
				logger.info("Fetch element bot terminated.");
				break;
			}
		}
		
		// Export to Excel
		String fileName = "Report\\" + "PageObjects_" + util.getTimestamp() + ".xlsx";
		new ExcelData().writeData(fileName, objectTable);
	}

	public void fetchElements(String pageName) {
		String[] skipTag = { "div", "section", "form" };
		List<String> skipTagList = Arrays.asList(skipTag);
		int counter = -1;
		List<WebElement> allElements = driver.findElements(By.xpath("//*[not(div)]"));
		for (WebElement element : allElements) {
			Properties prop = support.getExtrinsicProperty(element);
			int xLocation = Integer.parseInt(prop.getProperty("xLocation"));
			int yLocation = Integer.parseInt(prop.getProperty("yLocation"));
			if (xLocation>0 && yLocation>0) {
				Object iPropertyObject = support.getIntrinsictProperty(element);
				@SuppressWarnings("unchecked")
				Map<String, String> iProperty = (Map<String, String>) iPropertyObject;

				if (iProperty.size() > 0 && !skipTagList.contains(element.getTagName())) {
					logger.info(++counter + " : " + element.getTagName() + " : " + iProperty + "\t-> " + element.getText());
					ArrayList<Object> eachRow = new ArrayList<Object>();
					eachRow.add(counter);
					eachRow.add(element.getTagName());
					eachRow.add(pageName);
					eachRow.add(xLocation);
					eachRow.add(yLocation);
					eachRow.add(String.valueOf(iProperty));
					eachRow.add(element.getText());
					objectTable.add(eachRow);
				}
			}
		}
	}
}
