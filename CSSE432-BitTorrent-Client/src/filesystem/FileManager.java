package filesystem;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import gui.*;

public class FileManager
{
	public static byte[] readData(String fileName, int pieceIndex, int begin, int length)
	{
		Path filePath = Paths.get(fileName);
		byte[] filePiece = new byte[length];
		try
		{
			byte[] fullFile = Files.readAllBytes(filePath);
			filePiece = Arrays.copyOfRange(fullFile, pieceIndex*length+begin, (pieceIndex+1)*length+begin);
			
			LoggingPanel.log("[FileManager] Reading data for " + fileName + "...");
			return filePiece;
		}
		catch (IOException e)
		{
			LoggingPanel.log("Unable to read file " + fileName + ".");
		}
		
		LoggingPanel.log("[FileManager] Incorrectly reading file " + fileName + "...");
		return new byte[length];
	}
	
	public static void writeData(String fileName, int pieceIndex, int begin, byte[] data)
	{
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
		    try {
		    	LoggingPanel.log("[FileManager] Writing additional data to " + fileName + "...");
		    	Path filePath = Paths.get(fileName);
		    	byte[] currentFile = Files.readAllBytes(filePath);
		    	if (data.length * pieceIndex > currentFile.length){
		    		//Piece is outside range of current file
		    		byte[] newFile = new byte[data.length * (pieceIndex + 1)];
		    		newFile = Arrays.copyOfRange(currentFile, 0, currentFile.length);
		    		System.arraycopy(data, 0, newFile, pieceIndex*data.length, (pieceIndex+1)*data.length);
		    		
		    		FileOutputStream out = new FileOutputStream(fileName);
		    		out.write(newFile);
		    		out.close();
		    	} else {
		    		//Piece is inside range of current file
		    		System.arraycopy(data, 0, currentFile, pieceIndex*data.length, (pieceIndex+1)*data.length);
		    	}
		    }
		    catch (IOException e) {
		    	LoggingPanel.log("Unable to open existing file.");
		    }
		} else {
			//New file
			LoggingPanel.log("[FileManager] Creating file for " + fileName + "...");
			byte[] newFile = new byte[data.length * (pieceIndex + 1)];
			System.arraycopy(data, 0, newFile, pieceIndex*data.length, (pieceIndex+1)*data.length);
			FileOutputStream out;
			try
			{
				out = new FileOutputStream(fileName);
	    		out.write(newFile);
	    		out.close();	
			}
			catch (IOException e)
			{
				LoggingPanel.log("Unable to create file " + fileName + ".");
			}
		}
		LoggingPanel.log("[FileManager] Writing data for " + fileName + "...");
	}
}