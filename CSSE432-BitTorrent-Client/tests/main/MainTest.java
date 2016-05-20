package main;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import protocol.*;

public class MainTest
{
	//Here's the original examples as provided by http://www.bittorrent.org/beps/bep_0003.html
	@Test
	public void unparseString()
	{
		assertEquals("spam", BEncode.unparse("4:spam"));
	}
	
	@Test
	public void unparsePositiveInteger()
	{
		assertEquals(3, BEncode.unparse("i3e"));
	}
	
	@Test
	public void unparseNegativeInteger()
	{
		assertEquals(-3, BEncode.unparse("i-3e"));
	}
	
	@Test
	public void unparseZeroInteger()
	{
		assertEquals(0, BEncode.unparse("i0e"));
	}
	
	@Test
	public void unparseList()
	{
		List<Object> expected = new ArrayList<Object>();
		expected.add("spam");
		expected.add("eggs");
		assertEquals(expected, BEncode.unparse("l4:spam4:eggse"));
	}
	
	@Test
	public void unparseDictionary()
	{
		Map<String, Object> expected = new HashMap<String,Object>();
		expected.put("cow","moo");
		expected.put("spam","eggs");
		assertEquals(expected, BEncode.unparse("d3:cow3:moo4:spam4:eggse"));
	}
	
	@Test
	public void unparseListDictionary()
	{
		List<Object> expectedList = new ArrayList<Object>();
		expectedList.add("a");
		expectedList.add("b");
		Map<String, Object> expected = new HashMap<String, Object>();
		expected.put("spam", expectedList);
		assertEquals(expected, BEncode.unparse("d4:spaml1:a1:bee"));
	}
	
	@Test
	public void parseString()
	{
		assertEquals("4:spam", BEncode.parse("spam"));
	}
	
	@Test
	public void parsePositiveInteger()
	{
		assertEquals("i3e", BEncode.parse(3));
	}
	
	@Test
	public void parseNegativeInteger()
	{
		assertEquals("i-3e", BEncode.parse(-3));
	}
	
	@Test
	public void parseZeroInteger()
	{
		assertEquals("i0e", BEncode.parse(0));
	}
	
	@Test
	public void parseList()
	{
		List<Object> expected = new ArrayList<Object>();
		expected.add("spam");
		expected.add("eggs");
		assertEquals("l4:spam4:eggse", BEncode.parse(expected));
	}
	
	@Test
	public void parseDictionary()
	{
		Map<String, Object> expected = new HashMap<String,Object>();
		expected.put("cow","moo");
		expected.put("spam","eggs");
		assertEquals("d3:cow3:moo4:spam4:eggse", BEncode.parse(expected));
	}
	
	@Test
	public void parseListDictionary()
	{
		List<Object> expectedList = new ArrayList<Object>();
		expectedList.add("a");
		expectedList.add("b");
		Map<String, Object> expected = new HashMap<String, Object>();
		expected.put("spam", expectedList);
		assertEquals("d4:spaml1:a1:bee", BEncode.parse(expected));
	}
	
	//A couple other little things
	@Test
	public void parseWrongInt()
	{
		assertEquals("i0e",BEncode.parse(-0));
	}
	
	@Test
	public void unparseWrongZeroInt()
	{
		assertEquals(0,BEncode.unparse("i-0e"));
	}
	
	@Test
	public void unparseWrongLeadingZeroInt()
	{
		assertEquals(-3,BEncode.unparse("i-03e"));
	}
	
	//As far as I can see from the documentation, 
	// it doesn't look like there will be ints inside of lists or dictionaries
}