package protocol;

import java.util.*;

public class BEncode
{
	@SuppressWarnings("unchecked")
	public static String parse(Object input) {
		String parsedObj = "";
		if (input instanceof Integer) {
			// Parse Integer
			parsedObj = parseInt((Integer) input);
		} else if (input instanceof List<?>) {
			// Parse List
			parsedObj = parseList((List<Object>) input);
		} else if (input instanceof Map<?, ?>) {
			// Parse Map
			parsedObj = parseDict((Map<String, Object>)input);
		} else if (input instanceof String) {
			parsedObj = parseString((String) input);
		} else {
			parsedObj = parseString("ParseError");
		}
		return parsedObj;
	}
	
	private static String parseString(String input) {
		String toReturn = input.length() + ":" + input;
		System.out.println("String Parse: " + toReturn);
		return toReturn;
	}

	private static String parseInt(Integer input) {
		String toReturn = "i";
		//if(input < 0) {
		//	toReturn += "-";
		//}
		toReturn += input.toString() + "e";
		System.out.println("Integer Parse: " + toReturn);
		return toReturn;
	}
	
	private static String parseList(List<Object> input) {
		String toReturn = "l";
		for(Object o : input) {
			toReturn += parse(o);
		}
		toReturn += "e";
		System.out.println("List Parse: " + toReturn);
		return toReturn;
	}
	
	private static String parseDict(Map<String, Object> input) {
		String toReturn = "d";
		Set<String> keys = input.keySet();
		for(String k : keys) {
			toReturn += parseString(k);
			toReturn += parse(input.get(k));
		}
		toReturn += "e";
		System.out.println("Dictionary Parse: " + toReturn);
		return toReturn ;
	}
	
	public static Object unparse(String input)
	{
		char firstChar = input.charAt(0);
		switch (firstChar)
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
		String value = input.substring(0, input.length() - 1);
		if (value.charAt(0) == '0' && value.length() != 1)
		{
			// Error, leading zeros
			System.out.println("Invalid integer input");
		}
		if (value.length() > 1 && value.charAt(0) == '-' && value.charAt(1) == '0')
		{
			// Error, invalid input
			System.out.println("Invalid integer input");
		}
		System.out.println("Integer Unparse: " + value);
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
		while(!currString.equals("e")) {
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
		System.out.println("List Unparse: " + newList.toString());
		return newList;
	}

	private static Map<String, Object> dictUnparse(String input)
	{
		Map<String, Object> newDict = new HashMap<String,Object>();
		String currString = input;
		while(!currString.equals("e")) {
			Object value;
			int valLength = 0;
			int keyLength = Integer.parseInt(currString.charAt(0) + "");
			String key = currString.substring(2, keyLength+2);
			currString = currString.substring(keyLength+2);
			if(currString.charAt(0) == 'i') {
				String intString = extractObj(currString);
				value = intUnparse(intString.substring(1));
				valLength = intString.length();
			} else if (currString.charAt(0) == 'l') {
				String listString = extractObj(currString);
				value = listUnparse(listString.substring(1));
				valLength = listString.length();
			} else {
				valLength = Integer.parseInt(currString.charAt(0) + "")+2;
				value = currString.substring(2, valLength);
			}
			newDict.put(key, value);
			currString = currString.substring(valLength);
		}
		System.out.println("Dictionary Unparse: " + newDict.toString());
		return newDict;
	}
}