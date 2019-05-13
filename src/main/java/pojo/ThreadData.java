package pojo;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class ThreadData {

	public boolean isNewThread = true;
	public String threadName;

	public WebDriver driver;
	public String thisTab, previousTab, appName;

	public ArrayList<String> frameStack;
	public ArrayList<String> windowStack;

	public boolean sliderNavigateFlag = true;
}
