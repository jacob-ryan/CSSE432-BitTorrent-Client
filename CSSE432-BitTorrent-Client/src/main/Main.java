package main;

public class Main
{
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		System.out.println("Does nothing...");
	}
}

public class BencodeingParser
{
	public bencodeingUnparse(String input)
	{
		switch (input.charAt(0))
		{
		case "i":
		{
			//Integer
			
		}
		case "l":
		{
			//List
			
		}
		case "d":
		{
			//Dictionary
			
		}
		default:
		{
			if (Character.isDigit(input.charAt(0)))
			{
				//First character is a digit for length
				
			}
			else
			{
				//Error, first character is a unknown character
			
			}
		}
		}
	}
	
	private int intUnparse (String input)
	{
		String value = input.substring(1, input.length() - 1);
		if (value.charAt(0) == '0' && value.length != 1)
		{
			//Error, leading zeros
		}
		if (value.length > 1 && (value.charAt(0) == '-' && value.charAt(1) == '0'))
		{
			//Error, invalid input
		}
		return Integer.parseInt(value);
	}
	
	private ArrayList<???> listUnparse (String input)
	{
		
	}
	
	private HashMap<???> dictUnparse (String input)
	{
		
	}
}