package utility;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import utility.DriverSupport.ScreenShotType;

public class CustomLogger {
	String logFileName = "execution.log";
	String className;

	public CustomLogger() {
		this.className = Thread.currentThread().getStackTrace()[2].getClassName();
	}

	public CustomLogger(String className) {
		this.className = className;
	}

	public void info(String message) {
		info(message, false);
	}

	public void success(String message) {
		success(message, false);
	}

	public void error(String message) {
		error(message, true);
	}

	public void warning(String message) {
		warning(message, false);
	}

	public void debug(String message) {
		printLog("Debug", message, false);
	}

	public void info(String message, boolean screenshotFlag) {
		printLog("Info", message, screenshotFlag);
	}

	public void success(String message, boolean screenshotFlag) {
		printLog("Success", message, screenshotFlag);
	}

	public void error(String message, boolean screenshotFlag) {
		printLog("Error", message, screenshotFlag);
	}

	public void warning(String message, boolean screenshotFlag) {
		printLog("Warning", message, screenshotFlag);
	}

	private void printLog(String logType, String message, boolean screenshotFlag) {

		String text;
		PrintWriter out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy hh:mm:ss a");
		String currentTimestamp = sdf.format(new Date());

		switch (logType) {
		case "Info":
		case "Success":
			text = Thread.currentThread().getName() + " << " + logType + " >> [" + className + "] :\t" + message;
			System.out.println(text);
			break;

		case "Warning":
		case "Error":
			text = Thread.currentThread().getName() + "  << " + logType + " >> [" + className + "] :\t" + message;
			System.err.println(text);
			break;

		case "Debug":
			if (!Environment.DebugFlag)
				break;
			text = Thread.currentThread().getName() + " >> " + logType + " >> [" + className + "] :\t" + message;
			System.out.println(text);
		}

		try {
			out = new PrintWriter(new FileWriter(logFileName, true));
			out.println(currentTimestamp + " " + Thread.currentThread().getName() + " :[" + className + "]\t<< "
					+ logType + " >> " + message);
			out.close();

			if (screenshotFlag) {
				WebDriver driver = Environment.ThreadPool.get(Thread.currentThread().getName()).driver;
				new DriverSupport(driver).takeScreenShot(ScreenShotType.FullPage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
