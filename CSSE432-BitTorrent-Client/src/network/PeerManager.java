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
	
	public void addConnection(Connection connection)
	{
		synchronized (this.connections)
		{
			this.connections.add(connection);
		}
	}
	
	public void removeConnection(Connection connection)
	{
		synchronized (this.connections)
		{
			this.connections.remove(connection);
		}
	}
	
	public void addOutstandingChunk(RequestMessage rm)
	{
		synchronized (this.outstandingChunks)
		{
			this.outstandingChunks.add(rm);
		}
	}
	
	public void markChunkCompleted(PieceMessage pm)
	{
		synchronized (this.outstandingChunks)
		{
			for (int i = 0; i < this.outstandingChunks.size(); i += 1)
			{
				RequestMessage rm = this.outstandingChunks.get(i);
				if (rm.index == pm.index && rm.begin == pm.begin && rm.length == pm.piece.length)
				{
					this.outstandingChunks.remove(i);
					this.completedChunks.add(rm);
					checkCompletedPiece(pm);
					return;
				}
			}
		}
		throw new RuntimeException("[PeerManager] Trying to mark non-existant chunk completed.");
	}
	
	public void run()
	{
		LoggingPanel.log("[PeerManager] Generated peer-ID: " + new String(this.peerId));
		/*while (true)
		{
			// Need to get updated PeerInfo's from trackers.
			// Then try connecting to new peers if we have less than (say) 10 connections.
		}*/
		try
		{
			while (true)
			{
				Thread.sleep(100);
				synchronized (this.outstandingChunks)
				{
					if (this.outstandingChunks.size() < 10)
					{
						// Pick a random chunk to request from incomplete piece.
						int incomplete = 0;
						for (int i = 0; i < this.torrent.getNumberOfPieces(); i += 1)
						{
							if (!Bitfield.getPiece(this.getTorrent().getPieceBitfield(), i))
							{
								incomplete += 1;
							}
						}
						int index = (int) (Math.random() * incomplete);
						int current = 0;
						for (int i = 0; i < this.torrent.getNumberOfPieces(); i += 1)
						{
							if (!Bitfield.getPiece(this.getTorrent().getPieceBitfield(), i))
							{
								if (current == index)
								{
									// Found random incomplete piece.
									RequestMessage rm = new RequestMessage(index, 0, Torrent.PIECE_LENGTH);
									for (Connection connection : this.connections)
									{
										if (Bitfield.getPiece(connection.getPieceBitfield(), index))
										{
											connection.getConnectionWriter().queueMessage(rm);
										}
										else
										{
											LoggingPanel.log("[PeerManager] Tried to request piece that no peers had.");
										}
									}
									break;
								}
								current += 1;
							}
						}
					}
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
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
	
	private void checkCompletedPiece(PieceMessage pm)
	{
		int bytesCompleted = 0;
		for (int i = 0; i < this.completedChunks.size(); i += 1)
		{
			RequestMessage rm = this.completedChunks.get(i);
			if (rm.index == pm.index && rm.begin == pm.begin && rm.length == pm.piece.length)
			{
				bytesCompleted += rm.length;
			}
		}
		if (bytesCompleted == Torrent.PIECE_LENGTH)
		{
			Bitfield.setPiece(this.getTorrent().getPieceBitfield(), pm.index);
			// Queue announcement to all peers that we have received a full piece.
			synchronized (this.connections)
			{
				for (Connection connection : this.connections)
				{
					HaveMessage hm = new HaveMessage(pm.index);
					connection.getConnectionWriter().queueMessage(hm);
				}
			}
		}
	}
}