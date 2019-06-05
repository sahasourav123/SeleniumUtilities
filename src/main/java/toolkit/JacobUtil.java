package toolkit;

import java.io.File;
import java.util.ArrayList;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobUtil {

	public ArrayList<String> execute(String filePath, String macroName, String validationSheet,
			String... responseCells) {

		ArrayList<String> response = new ArrayList<>();

		String userDir = System.getProperty("user.dir");
		String datamodel = System.getProperty("sun.arch.data.model");
		String dllPath_x86 = userDir + "\\Dependency\\jacob-1.18-x86.dll";
		String dllPath_x64 = userDir + "\\Dependency\\jacob-1.18-x64.dll";

		if (datamodel.equalsIgnoreCase("64")) {
			System.load(dllPath_x64);
		} else {
			System.load(dllPath_x86);
		}

		File file = new File(filePath);
		ComThread.InitSTA(true);

		final ActiveXComponent excel = new ActiveXComponent("Excel.Application");
		try {
			excel.setProperty("EnableEvents", new Variant(false));
			Dispatch workbooks = excel.getProperty("Workbooks").toDispatch();
			Dispatch workBook = Dispatch.call(workbooks, "Open", file.getAbsolutePath()).toDispatch();

			// Calls the macro
			Dispatch.call(excel, "Run", new Variant(file.getName() + "!" + macroName));

			// Retrieve a value from Excel
			Dispatch sheets = Dispatch.get(workBook, "Worksheets").toDispatch();
			Dispatch sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get, new Object[] { validationSheet }, new int[0])
					.getDispatch();
			for (String responseCell : responseCells) {
				Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get, new Object[] { responseCell }, new int[1])
						.toDispatch();
				response.add(Dispatch.get(cell, "Value").toString());
			}

			// Saves and closes
			Dispatch.call(workBook, "Save");
			Dispatch.call(workBook, "Close", new Variant(true));

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			excel.invoke("Quit", new Variant[0]);
			ComThread.quitMainSTA();
			ComThread.Release();
		}

		return response;

	}

}
