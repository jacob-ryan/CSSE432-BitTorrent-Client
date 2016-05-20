package main;

import java.util.*;

import network.*;
import protocol.*;

public class Torrent
{
	private String name;
	private int pieceLength;
	private List<String> trackers;
	private String fileName;
	private long fileLength;
	private List<byte[]> pieceHashes;
	private byte[] pieceBitfield;
	private PeerManager peerManager;
	
	public Torrent(String name, int pieceLength)
	{
		this.name = name;
		this.pieceLength = pieceLength;
		this.trackers = new ArrayList<String>();
		this.peerManager = new PeerManager(this);
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
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public long getFileLength()
	{
		return this.fileLength;
	}
	
	public byte[] getPieceHash(int piece)
	{
		return this.pieceHashes.get(piece);
	}
	
	public byte[] getPieceBitfield()
	{
		return this.pieceBitfield;
	}
	
	public void addTracker(String tracker)
	{
		this.trackers.add(tracker);
	}
	
	public void setFile(String name, long length)
	{
		this.fileName = name;
		this.fileLength = length;
		this.pieceHashes.add(null);
		this.pieceBitfield = Bitfield.make(this.fileLength);
	}
	
	public byte[] getInfoHash()
	{
		return new byte[20];
	}
	
	public PeerManager getPeerManager()
	{
		return this.peerManager;
	}
}