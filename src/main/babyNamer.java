package main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import names.Names;

//main class, gets user input and sets up markov model
public class babyNamer {
	
	//builds the markov model and calls the names classs
	public static void setup(Scanner in, int min, int max, int num)
	{
		String[] letterCombos = new String[783];
		double[][][] names = new double[27][27][27];
		String[] possiblitiesArray = {"_", "_", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", 
				"r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String firstTwoLettersCombos = "";
		String possiblitiesLetters = "_abcdefghijklmnopqrstuvwxyz";
		
		int index = 0;
		for(String i : possiblitiesArray)
		{
			for(String j : possiblitiesArray)
			{
				letterCombos[index] = i+j;
				firstTwoLettersCombos += i+j;
			}
		}
		
		String temp;
		
		LinkedList<String> nameList = new LinkedList<String>();
		
		//iterate through each name in the file and create the markov model
		while(in.hasNext())
		{
			String name = in.next().toLowerCase();
			temp = name.replace(name, "__"+name+"__");
			nameList.add(name);
			//create the array based on how the letters appear
			for(int i = 0, j=i+1, k=i+2; i < temp.length()-2; i++, j++, k++)
			{
				String first = "" + temp.charAt(i);
				String second = "" + temp.charAt(j);
				int index1 = possiblitiesLetters.indexOf(first);
				int index2 = possiblitiesLetters.indexOf(second);
				names[index1][index2][possiblitiesLetters.indexOf(temp.charAt(k))] += 1;
			}
		}
		//create a name class and generate new names
		Names name = new Names(names, min, max, num, nameList);
		name.normalize();
		name.generateName();
	}
	//gets the user input
	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Select gender.  M for male, F for female: ");
		String gender = in.next();
		
		System.out.println("Select minimum name length: ");
		int min = in.nextInt();
		System.out.println("Select maximum name length: ");
		int max = in.nextInt();
		
		System.out.println("Select number of names to generate: ");
		int num = in.nextInt();
		
		String boyPath = "src/data/namesBoys.txt";
		String girlPath = "src/data/namesGirls.txt";
		
		if(gender.equalsIgnoreCase("M"))
		{
			try
			{
				in = new Scanner(new FileReader(boyPath));
			} 
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(gender.equalsIgnoreCase("F"))
		{
			try
			{
				in = new Scanner(new FileReader(girlPath));
			} 
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(gender.equalsIgnoreCase("j"))
		{
			try
			{
				in = new Scanner(new FileReader("src/data/test.txt"));
			} 
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else
		{
			System.out.println("Invalid entry");
		}
		//setsup the markov model
		setup(in, min, max, num);
	}
}
