package pojo;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class ThreadData {

	public String threadName;

	public WebDriver driver;
	public String appName;

	public ArrayList<String> frameStack;
	public ArrayList<String> windowStack;

}
