package protocol;

import java.util.*;

public class BEncode
{
	public static Object unparse(String input)
	{
		switch (input.charAt(0))
		{
			case 'i':
			{
				// Integer
				return intUnparse(input.substring(1));
			}
			case 'l':
			{
				// List
				return listUnparse(input.substring(1));
			}
			case 'd':
			{
				// Dictionary
				return dictUnparse(input.substring(1));
			}
			default:
			{
				char firstChar = input.charAt(0);
				if (Character.isDigit(firstChar))
				{
					// First character is a digit for length
					int itemLength = Integer.parseInt(firstChar + "")+2;
					return input.substring(2, itemLength);
				}
				else
				{
					// Error, first character is a unknown character

				}
			}
		}
		return null;
	}

	private static int intUnparse(String input)
	{
		String value = input.substring(1, input.length() - 1);
		if (value.charAt(0) == '0' && value.length() != 1)
		{
			// Error, leading zeros

		}
		if (value.length() > 1 && value.charAt(0) == '-' && value.charAt(1) == '0')
		{
			// Error, invalid input
		}
		System.out.println("Integer Parse: " + value);
		return Integer.parseInt(value);
	}
	
	private static String extractObj(String input) {
		int index = 0;
		while(input.charAt(index) != 'e') {
			index++;
		}
		return input.substring(0, index+1);
	}

	private static List<Object> listUnparse(String input)
	{
		List<Object> newList = new ArrayList<Object>();
		String currString = input;
		while(currString != "e") {
			Object item;
			int itemLength = 0;
			if(currString.charAt(0) == 'i') {
				String intString = extractObj(currString);
				item = intUnparse(intString);
				itemLength = intString.length();
			} else {
				itemLength = Integer.parseInt(currString.charAt(0) + "")+2;
				item = currString.substring(2, itemLength);
			}
			newList.add(item);
			currString = currString.substring(itemLength);
		}
		System.out.println("List Parse: " + newList.toString());
		return newList;
	}

	private static Map<String, Object> dictUnparse(String input)
	{
		Map<String, Object> newDict = new HashMap<String,Object>();
		String currString = input;
		while(currString != "e") {
			Object value;
			int valLength = 0;
			int keyLength = Integer.parseInt(currString.charAt(0) + "");
			String key = currString.substring(2, keyLength+2);
			currString = currString.substring(keyLength+2);
			if(currString.charAt(0) == 'i') {
				String intString = extractObj(currString);
				value = intUnparse(intString);
				valLength = intString.length();
			} else if (currString.charAt(0) == 'l') {
				String listString = extractObj(currString);
				value = listUnparse(listString);
				valLength = listString.length();
			} else {
				valLength = Integer.parseInt(currString.charAt(0) + "")+2;
				value = currString.substring(2, valLength);
			}
			newDict.put(key, value);
			currString = currString.substring(valLength);
		}
		System.out.println("Dictionary Parse: " + newDict.toString());
		return newDict;
	}
}