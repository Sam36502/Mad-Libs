
package ch.pearcenet.madlib;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
	
	//The Path to the files
	private static String path;
	
	//File object of the mad-lib currently open
	private static File currentFile;
	
	//Open an input scanner
	private static Scanner input = new Scanner(System.in);
	
	//File Attributes
	private static String prefix;
	private static String filename;
	
	public static void main(String[] args) {
		
		//List all the available MadLibs
		System.out.print("Enter the path to search for Mad-Libs: ");
		path = input.nextLine();
		
		//Remove the '/' at the end of the path, if it exists
		if (path.charAt(path.length() - 1) == '/') {
			path = path.substring(0, path.length() - 1);
		}
		String[] madlibs = FileLoad.listAvail(path);
		
		//If no Mad-Libs were found
		if (madlibs == null) {
			System.out.println("No Mad-Libs could be found for that directory.");
			System.exit(0);
		}
		
		//Prompt the user to pick one of the MadLibs
		System.out.println("\nEnter the name of a Mad-Lib from the list: ");
		boolean isValid = false;
		
		//Validate the user input
		do {
			System.out.print("> ");
			filename = input.nextLine();
			
			for (String curr: madlibs) {
				if (curr.equals(filename)) {
					isValid = true;
					break;
					
				} else if (filename.equals(curr.substring(0, curr.indexOf('.')))) {
					filename = curr;
					isValid = true;
					break;
				}
			}
			
			//Error message
			if (!isValid) {
				System.out.println("Please select a valid option from the list.");
			}
		} while (!isValid);
		
		//Get file contents
		currentFile = new File(path + "/" + filename);
		String content = FileLoad.getContent(path + "/" + filename);
		
		//Check if the Mad-Lib has a prefix, otherwise use full questions
		prefix = FileLoad.getAttribute(currentFile, "prefix");
		if (prefix == null) {
			prefix = "Enter ";
		} else {
			prefix = "Enter " + prefix + " ";
		}
		
		content = askPresetQs(content);
		content = askStoryQs(content);

		//Print out the result
		printStoryHeader();
		System.out.println("\nFinal Story:\n------------\n" + content);
	}
	
	
	
	// Gets all the main attributes from the file, stores them and prints them
	private static void printStoryHeader() {
		
		//Check if the Mad-Lib has a title, otherwise use the filename
		String title = FileLoad.getAttribute(currentFile, "title");
		if (title == null) {
			title = filename;
		}
		
		//Display title
		System.out.println("\n"+title+":");
		for (int i=0; i<=title.length(); i++) System.out.print("-");
		System.out.println(" ");
		
		//Check if the Mad-Lib has an author, otherwise leave it out
		String author = FileLoad.getAttribute(currentFile, "author");
		if (author != null) {
			System.out.println("by "+author);
		}
		System.out.println("\n");
		
	}
	
	// Ask all the questions in the story and insert results into content
	private static String askStoryQs(String content) {
		ArrayList<String> storyQuestions = new ArrayList<>();
		
		//Read through the file and find all the main story questions
		while (content.contains("{")) {
			
			//Add the question to the list of questions
			storyQuestions.add(content.substring(content.indexOf("{") + 1,
					content.indexOf("}")));
			
			//Replace the question with a '%placeholder%'
			content = content.substring(0, content.indexOf("{")) +
						"%placeholder%" +
						content.substring(content.indexOf("}") + 1);
		}
		
		//Ask all the questions and replace them in the text
		for (String currQ: storyQuestions) {
			
			//Check if the question is escaped
			if (currQ.charAt(0) == '!') {
				System.out.println("Enter " + currQ);
			} else {
				System.out.println(prefix + currQ);
			}
			System.out.print("> ");
			
			content = content.replaceFirst("%placeholder%", input.nextLine());
		}
		
		return content;
	}
	
	// Ask all the preset questions and insert results into the story
	private static String askPresetQs(String content) {
		ArrayList<String> presetQuestions = new ArrayList<>();
		
		//Get the preset questions
		String presets = FileLoad.getAttribute(currentFile, "presets");
		if (presets != null) {
			String[] presetQs = presets.split(",");
			
			for (String curr: presetQs) {
				String question = FileLoad.getAttribute(currentFile, curr.trim());
				if (question != null) {
					presetQuestions.add(curr.trim() + "-" + question);
				}
			}
			
		}
		
		//Ask all the preset questions and insert the answers into content
		for (String currQ: presetQuestions) {
			String presetName = currQ.split("-")[0];
			String question = currQ.split("-")[1];
			
			System.out.println(prefix + question);
			System.out.print("> ");
			
			content = content.replaceAll("%" + presetName + "%", input.nextLine());
		}
		
		return content;
	}

}
