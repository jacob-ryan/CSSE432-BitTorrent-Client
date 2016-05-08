package main;

import java.util.*;

public class Torrent
{
	private String name;
	private int pieceLength;
	private List<String> trackers;
	private List<String> fileNames;
	private List<Long> fileLengths;
	private List<byte[]> fileHashes;
	private List<Connection> connections;
	
	public Torrent(String name, int pieceLength)
	{
		this.name = name;
		this.pieceLength = pieceLength;
		this.trackers = new ArrayList<String>();
		this.fileNames = new ArrayList<String>();
		this.fileLengths = new ArrayList<Long>();
		this.fileHashes = new ArrayList<byte[]>();
		this.connections = new ArrayList<Connection>();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getPieceLength()
	{
		return this.pieceLength;
	}
	
	public List<String> getTrackers()
	{
		return this.trackers;
	}
	
	public String getFileName(int i)
	{
		return this.fileNames.get(i);
	}
	
	public long getFileLength(int i)
	{
		return this.fileLengths.get(i);
	}
	
	public byte[] getFileHash(int i)
	{
		return this.fileHashes.get(i);
	}
	
	public int getNumFiles()
	{
		return this.fileNames.size();
	}
	
	public void addTracker(String tracker)
	{
		this.trackers.add(tracker);
	}
	
	public void addFile(String name, long length, byte[] hash)
	{
		this.fileNames.add(name);
		this.fileLengths.add(length);
		this.fileHashes.add(hash);
	}
}