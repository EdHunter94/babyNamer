package names;

import java.util.LinkedList;
import java.util.Random;

//class that genenates the new names based on the markov model
public class Names {
	private double[][][] names;
	private int min;
	private int max;
	private int numNames;
	private String possiblitiesLetters = "_abcdefghijklmnopqrstuvwxyz";
	private LinkedList<String> nameList = new LinkedList<String>();
	
	//constructor
	public Names(double[][][] name, int min, int max, int numNames, LinkedList<String> nameList)
	{
		names = name;
		this.min = min;
		this.max = max;
		this.numNames = numNames;
		this.nameList = nameList;
	}
	
	//method that takes the probability that a certain letter will appear given the two preceding letters
	public void normalize()
	{
		int total = 0;
		//loop through the array and find the total
		for(int i = 0; i < names.length; i++)
		{
			for(int j = 0; j < names.length; j++)
			{
				for(int k = 0; k < 27; k++)
				{
					total += names[i][j][k];
				}
			}
		}
		//then divide each element by the total, will add to one (stochastic)
		for(int i = 0; i < names.length; i++)
		{
			for(int j = 0; j < names.length; j++)
			{
				for(int k = 0; k < 27; k++)
				{
					names[i][j][k] /= total;
				}
			}
		}
	}
	//method to generate new names
	public void generateName()
	{
		int generated = 0;
		while(generated < numNames)
		{
			String name = "";
			//create a random float between 0 and 1
			float pos = new Random().nextFloat();
			//iterate through the array and add letters to the new name based on their probabilities
			for(int i = 0; i < 27; i++)
			{
				for(int j = 0; j < 27; j++)
				{
					for(int k = 0; k < 27; k++)
					{
						if(pos < names[i][j][k] && name.length() < min)
						{
							name += possiblitiesLetters.charAt(k);
							j = k;
						}
						pos -= names[i][j][k];
					}
				}
			}
			if(!nameList.contains(name) && name.length() <= max)
			{
				System.out.println(name);
				generated++;
			}
		}
		
	}
}
