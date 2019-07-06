package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import toolkit.ExcelData;
import toolkit.SupportUtil;
import utility.DriverSupport.ScreenShotType;

public class ObjectRepository {

	WebDriver driver;
	DriverSupport support;
	CustomLogger logger;
	SupportUtil util;
	ArrayList<ArrayList<Object>> objectTable;
	JDialog dialog;
	
	public ObjectRepository(WebDriver driver) {
		this.driver = driver;
		support = new DriverSupport(driver);
		logger = new CustomLogger();
		util = new SupportUtil();
		
		JOptionPane pane = new JOptionPane();  
		dialog = pane.createDialog("");  
		dialog.setAlwaysOnTop(true);
	}

	public void scanWindow() {
		String currentWindow = "";
		try {
			currentWindow = driver.getWindowHandle();
		}catch(Exception e) {
			// Just Ignore
		}
		// Create a choice with list of open windows to select & proceed
		Set<String> windows = driver.getWindowHandles();
		
		List<String> windowNames = new ArrayList<String>();
		for (String handle : windows) {
			windowNames.add(driver.switchTo().window(handle).getTitle());
		}
		UIManager.put("OptionPane.cancelButtonText", "Exit");
		UIManager.put("OptionPane.okButtonText", "Select");
		String selectedWindow = (String) JOptionPane.showInputDialog(
				dialog,	"Choose window...",
		        "Selenium Utilies - Fetch Elements",
		        JOptionPane.QUESTION_MESSAGE, null, 
		        windowNames.toArray(),
		        windowNames.get(0));
		if(selectedWindow!=null){
			support.switchToWindow(selectedWindow, 3);
		} else {
			driver.switchTo().window(currentWindow);
		}
	}
	
	public void developRepository() {
		objectTable = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> header = new ArrayList<Object>();
		header.add("Element Counter");
		header.add("Tag");
		header.add("Window");
		header.add("Frame");
		header.add("X-Cordinate");
		header.add("Y-Cordinate");
		header.add("Locators");
		header.add("Text");
		objectTable.add(header);
		
		logger.info("Fetch element bot initiated...");
		String[] options = {"Fetch Elements", "Select Window", "Exit..."};
		String userInstruction = "Developed By: Sourav Saha\n\n"
				+ "1. Fetch - Extract WebElements from Page\n"
				+ "2. Scan Windows - Scan and select from opened browser window\n"
				+ "3. Exit - Exit and export all elements in excel";
		while (true) {
			
			// Pop-up modal for user interaction
			int n = (Integer) JOptionPane.showOptionDialog(dialog, userInstruction,  "Interactive Command Window", JOptionPane.PLAIN_MESSAGE, 1, null, options, 0);
			try {
				if (n == 0) {
					String pageName = driver.getTitle();
					logger.info("Title: " + pageName);
					logger.info("Url: " + driver.getCurrentUrl());
					
					// Fetch elements and take screenshot
					dumpWebElements(pageName, "");
					support.takeScreenShot(ScreenShotType.FullPage);

				} else if (n == 1) {
					// Scan windows
					scanWindow();
					
				} else {
					logger.info("Fetch element bot terminated. Exporting WebElement Dump");
					break;
				}
			}catch(Exception e) {
				logger.error("Something went wrong: See StackTrace");
				e.printStackTrace();
			}
		}
		
		// Export to Excel
		String fileName = "Report\\" + "PageObjects_" + util.getTimestamp() + ".xlsx";
		new ExcelData().writeData(fileName, objectTable);
	}

	public void dumpWebElements(String pageName, String frame) {
		List<String> excludeTags = Arrays.asList("div", "section", "form");
		int counter = 0;
		List<WebElement> allElements = driver.findElements(By.xpath("//*[not(div)]"));
		for (WebElement element : allElements) {
			Properties prop = support.getExtrinsicProperty(element);
			int xLocation = Integer.parseInt(prop.getProperty("xLocation"));
			int yLocation = Integer.parseInt(prop.getProperty("yLocation"));
			if (xLocation>0 && yLocation>0) {
				Object iPropertyObject = support.getIntrinsictProperty(element);
				@SuppressWarnings("unchecked")
				Map<String, String> iProperty = (Map<String, String>) iPropertyObject;

				if (iProperty.size() > 0 && !excludeTags.contains(element.getTagName())) {
					logger.info(++counter + " : " + element.getTagName() + " : " + iProperty + "\t-> " + element.getText());
					ArrayList<Object> eachRow = new ArrayList<Object>();
					eachRow.add(counter);
					eachRow.add(element.getTagName());
					eachRow.add(pageName);
					eachRow.add(frame);
					eachRow.add(xLocation);
					eachRow.add(yLocation);
					eachRow.add(String.valueOf(iProperty));
					eachRow.add(element.getText());
					objectTable.add(eachRow);
				}
				
				// navigate to nested iFrame and fetch element
				if(element.getTagName().equalsIgnoreCase("iframe")) {
					String frameName = element.getAttribute("id");
					driver.switchTo().frame(element);
					dumpWebElements(pageName, frameName);
					driver.switchTo().parentFrame();
				}
			}
		}
	}
}
