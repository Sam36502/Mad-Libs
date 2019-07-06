
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
	
	public static void main(String[] args) {
		
		//List all the available MadLibs
		System.out.print("Enter the path to search for Mad-Libs: ");
		path = input.nextLine();
		String[] madlibs = FileLoad.listAvail(path);
		
		//Prompt the user to pick one of the MadLibs
		System.out.println("\nEnter the name of an Mad-Lib from the list: ");
		boolean isValid = false;
		String selected;
		
		//Validate the user input
		do {
			System.out.print("> ");
			selected = input.nextLine();
			
			for (String curr: madlibs) {
				if (curr.equals(selected)) {
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
		currentFile = new File(path + "\\" + selected);
		String content = FileLoad.getContent(path + "\\" + selected);
		ArrayList<String> storyQuestions = new ArrayList<>();
		
		//Get the preset questions
		while (content.contains("%")) {
			
		}
		
		//Read through the file and find all the main story questions
		while (content.contains("{")) {
			storyQuestions .add(content.substring(content.indexOf("{") + 1,
					content.indexOf("}")));
			content = content.substring(0, content.indexOf("{")) +
						"%placeholder%" +
						content.substring(content.indexOf("}") + 1);
		}
		
		//Check if the Mad-Lib has a title, otherwise use the filename
		String title = FileLoad.getAttribute(currentFile, "title");
		if (title == null) {
			title = selected;
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
		
		//Check if the Mad-Lib has a prefix, otherwise use full questions
		String prefix = FileLoad.getAttribute(currentFile, "prefix");
		if (prefix == null) {
			prefix = "Enter ";
		} else {
			prefix = "Enter " + prefix + " ";
		}
		
		//Ask all the preset questions
		for ()
		
		//Ask all the questions in the story and insert results into content
		for (String currQ: storyQuestions) {
			System.out.println(prefix + currQ);
			System.out.print("> ");
			
			content = content.replaceFirst("%placeholder%", input.nextLine());
		}
		
		//Print out the result
		System.out.println("\nFinal Story:\n------------\n\n" + content);
	}

}
