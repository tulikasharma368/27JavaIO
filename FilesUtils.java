package io.employeePayroll;

import java.io.File;

public class FilesUtils {

	public static boolean deleteFiles(File contentsToBeDeleted) {
		File[] allContents = contentsToBeDeleted.listFiles();
		if(allContents != null) {
			for(File file : allContents) {
				deleteFiles(file);
			}
		}
		return contentsToBeDeleted.delete();
	}

}
