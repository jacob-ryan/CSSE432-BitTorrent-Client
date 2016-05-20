package filesystem;

import gui.*;

public class FileManager
{
	public static byte[] readData(String fileName, int pieceIndex, int begin, int length)
	{
		LoggingPanel.log("[FileManager] Reading data for " + fileName + "...");
		return new byte[length];
	}
	
	public static void writeData(String fileName, int pieceIndex, int begin, byte[] data)
	{
		LoggingPanel.log("[FileManager] Writing data for " + fileName + "...");
	}
}