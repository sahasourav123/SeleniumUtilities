package utility;

import java.util.HashMap;
import java.util.Map;

import pojo.ThreadData;
import toolkit.SupportUtil;

/**
 * Environment Class will keep track of all variable required through the execution.
 * This variables are not Thread depended variables.
 * @author SouravS
 *
 */
public class Environment {
	
	public static boolean ScreenshotFlag = false, DebugFlag = false;
	public static String MachineIP = new SupportUtil().getMachineIP();
	public static String MachineName = new SupportUtil().getMachineName();
	public static Map<String, ThreadData> ThreadPool = new HashMap<String, ThreadData>();

	public static boolean isScreenshotFlag() {
		return ScreenshotFlag;
	}

	public static void setScreenshotFlag(boolean screenshotFlag) {
		ScreenshotFlag = screenshotFlag;
	}

	public static boolean isDebugFlag() {
		return DebugFlag;
	}

	public static void setDebugFlag(boolean debugFlag) {
		DebugFlag = debugFlag;
	}

	public static String getMachineIP() {
		return MachineIP;
	}
	
	public static String getMachineName() {
		return MachineName;
	}

}
