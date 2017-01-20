/*
 * Sample code for CS 2610 Homework 1
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
	public ArrayList<String> wtable;
	public ArrayList<Integer> ftable;

	public Dictionary() throws FileNotFoundException {
		System.out.println("Loading dictionary...");
		
		wtable = new ArrayList<String>();
		ftable = new ArrayList<Integer>();
		Scanner input = new Scanner(new File("./wordf.txt"));
		String buffer = new String("");
		
		while(input.hasNext()){	
			buffer = input.nextLine();
			Scanner scan = new Scanner(buffer).useDelimiter(",");
			wtable.add(scan.next());
			ftable.add(scan.nextInt());
		}
		
		input.close();
		System.out.println("Dictionary Loaded.");
	}
	
	
}
