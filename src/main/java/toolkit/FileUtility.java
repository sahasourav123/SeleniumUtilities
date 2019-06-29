package toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import pojo.DesiredException;
import utility.CustomLogger;

public class FileUtility {
	
	CustomLogger logger;
	
	public FileUtility() {
		this.logger = new CustomLogger(
				Thread.currentThread().getStackTrace()[2].getClassName() + " : " + this.getClass().getSimpleName());
	}

	
	/**
	 * Read PDF/Text File and Return the Extracted Text as Array of lines
	 * @param Absolute path of the file
	 * @return 
	 */
	public String[] fileHandler(String filePath) {
		String[] lines = null;
		try {
			if (filePath.toLowerCase().endsWith(".pdf")) {
				PDDocument document = PDDocument.load(new File(filePath));
				String text = new PDFTextStripper().getText(document);
				lines = text.split(System.getProperty("line.separator"));
				document.close();
				logger.success("PDF File Loaded Successfully", false);
				
			} else if (filePath.toLowerCase().endsWith(".txt")) {
				String text = IOUtils.toString(new FileInputStream(filePath), "UTF-8");
				lines = text.split(System.getProperty("line.separator"));
				logger.success("TXT File Loaded Successfully", false);
			} else {
				logger.error("File Type not handled for parsing", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	/**
	 * Rename the file name in file system, Full file-folder path is required
	 * @param oldFileFullPath - existing absolute of file/folder
	 * @param newFileFullPath - 
	 * @throws DesiredException - If file/folder with same name exists or renaming failed.
	 */
	public boolean renameFile(String oldFileFullPath, String newFileFullPath) throws DesiredException {

		// File (or directory) with old name
		File file = new File(oldFileFullPath);
		
		// File (or directory) with new name
		File file2 = new File(newFileFullPath);
				
		if (!file.exists()) {
			logger.error("File doesn't exist: " + newFileFullPath);
			return false;
		}		

		if (file2.exists()) {
			logger.error("File already exists as: " + newFileFullPath);
			return false;
		}

		// Rename file (or directory)
		boolean success = file.renameTo(file2);

		if (!success) {
			logger.error("Renaming Failed");
		}
		
		return success;
	}
	
	public boolean isFile(String absolutePath) {
		File file = new File(absolutePath);
		return file.isFile();
	}
}
