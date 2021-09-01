package io.employeePayroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class NIOFileAPITest {
	private static String HOME = System.getProperty("user.home");
	private static String PLAY_WITH_NIO = "TempPlayGround";
	
	@Test
	public void givenPath_WhenChecked_ThenConfirm() throws IOException{
		
		//checks file exist
		Path homePath = Paths.get(HOME);
		//System.out.println(HOME);
		//Assert.assertTrue(Files.exists(homePath));
		
		//Delete files and check files don't exist
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if(Files.exists(playPath)) 
			FilesUtils.deleteFiles(playPath.toFile());
		else
			System.out.println("Not found");
		Assert.assertTrue(Files.notExists(playPath));
		
		//Create directory
		Files.createDirectory(playPath);
		Assert.assertTrue(Files.exists(playPath));
		
		//Create Files
		IntStream.range(1, 10).forEach(cntr ->{
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				if(Files.notExists(tempFile))
//					System.out.println("already");
					Files.createFile(tempFile);
			} catch (IOException e) {
				System.out.println("No files to create");
			}
			Assert.assertTrue(Files.exists(tempFile));
		});
		
		//List files, directories with extension
		Files.list(playPath).forEach(System.out::println);
		//Files.newDirectoryStream(playPath).forEach(System.out::println);
		//Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp")).forEach(System.out::println);
	}

}
