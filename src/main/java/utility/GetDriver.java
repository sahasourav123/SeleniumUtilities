package utility;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class GetDriver {
	WebDriver driver;
	int implicit_wait = 10;

	/**
	 * Create an IE Driver Instance
	 * @param initialURL
	 * @return
	 */
	public WebDriver ieDriver(String initialURL) {

		System.setProperty("webdriver.ie.driver", "Dependency\\IEDriverServer.exe");
		InternetExplorerOptions options = new InternetExplorerOptions();

		options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		options.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
		options.setCapability(InternetExplorerDriver.BROWSER_ATTACH_TIMEOUT, 10000);
		options.setCapability(InternetExplorerDriver.SILENT, true);
		options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, initialURL);
		options.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, true);
		// options.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
		this.driver = new InternetExplorerDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicit_wait, TimeUnit.SECONDS);

		// Set browser zoom level 100%
		driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.CONTROL, "0"));

		return driver;
	}

	/**
	 * Create a Chrome Driver Instance
	 * @return
	 */
	public WebDriver chromeDriver() {

		System.setProperty("webdriver.chrome.driver", "Dependency\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicit_wait, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * Create a Firefox Driver Instance
	 * @return
	 */
	public WebDriver firefoxDriver() {

		System.setProperty("webdriver.gecko.driver", "Dependency\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicit_wait, TimeUnit.SECONDS);
		return driver;
	}

	@SuppressWarnings("unused")
	/*
	 * IMPORTANT: Method is not yet developed
	 */
	private void killAttchedBrowserProcess() {
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		final int index = jvmName.indexOf('@');
		if (index > 1) {
			try {
				String processId = Long.toString(Long.parseLong(jvmName.substring(0, index)));
				Scanner scan = new Scanner(Runtime.getRuntime()
						.exec("wmic process where (ParentProcessId=" + processId + ") get Caption,ProcessId")
						.getInputStream());
				scan.useDelimiter("\\A");
				String childProcessIds = scan.hasNext() ? scan.next() : "";
				List<String> drivers = new ArrayList<String>();
				String[] splited = childProcessIds.split("\\s+");
				for (int i = 0; i < splited.length; i = i + 2) {
					if ("IEDriverServer.exe".equalsIgnoreCase(splited[i])) {
						drivers.add(splited[i + 1]);
					}
				}
				scan.close();
			} catch (Exception e) {

			}
		}
		System.out.println("");
	}
}
