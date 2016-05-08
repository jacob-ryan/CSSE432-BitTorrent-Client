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

			}
			case 'l':
			{
				// List

			}
			case 'd':
			{
				// Dictionary

			}
			default:
			{
				if (Character.isDigit(input.charAt(0)))
				{
					// First character is a digit for length

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
		return Integer.parseInt(value);
	}

	private static List<Object> listUnparse(String input)
	{
		return null;
	}

	private static Map<String, Object> dictUnparse(String input)
	{
		return null;
	}
}