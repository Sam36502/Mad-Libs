package ch.pearcenet.madlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoad {
	
	//Gets an attribute from a madlib file
	public static String getAttribute(File madlib, String attribute) {
		if (FileLoad.isMadlib(madlib)) {
			try {
				//Open file stream
				FileInputStream fis = new FileInputStream(madlib);
				Scanner in = new Scanner(fis);
				
				//Read through the whole file and check each line if it matches the format
				while (in.hasNextLine()) {
					String curr = in.nextLine();
					if (curr.contains("#!") &&
						attribute.equals(curr.substring(curr.indexOf("#!") + 2, curr.indexOf("=")).trim())) {
						in.close();
						fis.close();
						return curr.substring(curr.indexOf("=")+1).trim();
					}
				}
				
				//No attributes were found
				in.close();
				fis.close();
				return null;
				
			//If the file couldn't be opened
			} catch (IOException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	//Reads the whole contents of a madlib file and
	public static String getContent(String filename) {
		try {
			//Open file
			FileInputStream fis = new FileInputStream(filename);
			Scanner in = new Scanner(fis);
			StringBuilder result = new StringBuilder();
			
			//Read each line from the file
			while (in.hasNextLine()) {
				String next = in.nextLine();
				
				//Check and remove comments
				if (next.contains("#")) {
					next = next.substring(0, next.indexOf("#"));
				}
				
				//Check if the line has any content
				if (next.length() != 0) {
					result = result.append(next + "\n");
				}
			}
			
			//Close file
			in.close();
			fis.close();
			return result.toString();
		}catch (IOException e) {
			System.out.println("[ERROR] Program failed to load the file");
			return null;
		}
	}
	
	//Lists all the available madlib files for any given folder
	public static String[] listAvail(String path) {
		File file = new File(path);
		System.out.println("Searching for Mad-Lib files in '"+file.getAbsolutePath()+"'...");
		ArrayList<String> result = new ArrayList<>();
		
		//Check if the path is a valid directory
		if (file.isDirectory()) {
			boolean foundFile = false;
			
			//get list of files in this folder
			for (String current: file.list()) {
				
				//If the file is in ad-lib format
				File toCheck = new File(path + "/" + current);
				if (FileLoad.isMadlib(toCheck) && toCheck.isFile()) {
					System.out.println("Found: " + current);
					result.add(current);
					foundFile = true;
				}
			}
			
			//If no adlib files were found
			if (!foundFile) {
				return null;
			}
			
			return result.toArray(new String[result.size()]);
		} else if (file.isFile()) {
			System.out.println("That's a file. Please enter a directory.");
			return null;
		} else {
			System.out.println("That directory doesn't exist.");
			return null;
		}
	}
	
	//Returns whether or not a file is in the madlib file format
	public static boolean isMadlib(File file) {
		
		//Check the postfix for '.txt' or '.mdlb'
		String postfix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.'));
		if (".txt".equals(postfix) || ".mdlb".equals(postfix)) {
			try {
				//Open file stream
				FileInputStream fis = new FileInputStream(file);
				Scanner in = new Scanner(fis);
				
				//Read through the whole file and check each line if it matches the format
				while (in.hasNextLine()) {
					String curr = in.nextLine().replaceAll(" ", "");
					if (curr.contains("#!") &&
						"format=madlibs".equals(curr.substring(curr.indexOf("#!") + 2))) {
						in.close();
						fis.close();
						return true;
					}
				}
				
				//No attributes were found
				in.close();
				fis.close();
				return false;
				
			//If the file couldn't be opened
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
	}

}
