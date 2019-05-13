package utility;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import pojo.ThreadData;

public class Environment {

	public static String LogPath;
	public static boolean ScreenshotFlag, Debug_Flag;
	public static String MachineIP;
	public static String Version;
	public static Properties Properties;
	public static Map<String, ThreadData> ThreadPool;

	public void init() throws Exception {
		Debug_Flag = false;
		MachineIP = new SupportUtil().machineIP();

		// Load Properties File
		Properties = new Properties();
		FileInputStream configFile = new FileInputStream("Dependency\\config.properties");
		Properties.load(configFile);

		// Initialize Global Variables
		ThreadPool = new HashMap<String, ThreadData>();

		// Set Variables
		Version = "1.0.20190403.a";
		ScreenshotFlag = false;
	}

}
