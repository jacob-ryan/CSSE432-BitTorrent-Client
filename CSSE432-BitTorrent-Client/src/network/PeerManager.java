package network;

import java.util.*;

import gui.*;
import main.*;
import protocol.*;

public class PeerManager extends Thread
{
	private Torrent torrent;
	private byte[] peerId;
	private ConnectionListener connectionListener;
	private List<Connection> connections;
	private List<RequestMessage> outstandingChunks;
	private List<RequestMessage> completedChunks;
	
	public PeerManager(Torrent torrent)
	{
		this.torrent = torrent;
		this.peerId = generatePeerId();
		this.connectionListener = new ConnectionListener(this);
		this.connections = new ArrayList<Connection>();
		this.outstandingChunks = new ArrayList<RequestMessage>();
		this.completedChunks = new ArrayList<RequestMessage>();
		
		this.start();
	}
	
	public Torrent getTorrent()
	{
		return this.torrent;
	}
	
	public byte[] getPeerId()
	{
		return this.peerId;
	}
	
	public synchronized void addConnection(Connection connection)
	{
		this.connections.add(connection);
	}
	
	public synchronized void removeConnection(Connection connection)
	{
		this.connections.remove(connection);
	}
	
	public void run()
	{
		LoggingPanel.log("[PeerManager] Generated peer-ID: " + new String(this.peerId));
		//while (true)
		{
			// Need to get updated PeerInfo's from trackers.
			// Then try connecting to new peers if we have less than (say) 10 connections.
		}
	}
	
	private byte[] generatePeerId()
	{
		byte[] id = new byte[20];
		for (int i = 0; i < id.length; i += 1)
		{
			id[i] = (byte) (Math.random() * ((90 - 20) - 65) + 65);
		}
		return id;
	}
}