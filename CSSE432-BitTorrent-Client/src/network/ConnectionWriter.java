package network;

import java.io.*;
import java.util.*;

import protocol.*;

public class ConnectionWriter extends Thread
{
	private Connection connection;
	private LinkedList<Message> queue;
	private final Object lock = new Object();
	
	public ConnectionWriter(Connection connection)
	{
		this.connection = connection;
		this.queue = new LinkedList<Message>();
		this.start();
	}
	
	public void queueMessage(Message message)
	{
		synchronized (this.lock)
		{
			this.queue.addLast(message);
			this.lock.notify();
		}
	}
	
	public void handleRequestMessage(RequestMessage rm)
	{
		byte[] pieceData = FileManager.readData(this.connection.getTorrent().getFileName(), rm.index, rm.begin, rm.length);
		PieceMessage pm = new PieceMessage(rm.index, rm.begin, pieceData);
		queueMessage(pm);
	}
	
	public void handlePieceMessage(PieceMessage pm)
	{
		HaveMessage hm = new HaveMessage(pm.index);
		queueMessage(hm);
	}
	
	public void handleCancelMessage(CancelMessage cm)
	{
		synchronized (this.lock)
		{
			for (int i = 0; i < this.queue.size(); i += 1)
			{
				Message test = this.queue.get(i);
				if (test instanceof RequestMessage)
				{
					RequestMessage rm = (RequestMessage) test;
					if (rm.index == cm.index && rm.begin == cm.begin && rm.length == cm.length)
					{
						this.queue.remove(i);
						return;
					}
				}
			}
		}
	}
	
	public void run()
	{
		try
		{
			// Fully connected to peer (handshake sent/read and verified).
			// Must first send our piece bitfield to the peer.
			BitfieldMessage bm = new BitfieldMessage(this.connection.getTorrent().getPieceBitfield());
			bm.sendMessage(this.connection.getOutputStream());
			
			while (true)
			{
				Message toSend = null;
				synchronized (this.lock)
				{
					try
					{
						this.lock.wait(60 * 1000);
					}
					catch (InterruptedException e)
					{
						// Timed out, need to send KeepaliveMessage.
						this.queue.addFirst(new KeepaliveMessage());
					}
					if (this.queue.size() > 0)
					{
						toSend = this.queue.removeFirst();
					}
				}
				if (toSend != null)
				{
					toSend.sendMessage(this.connection.getOutputStream());
				}
			}
		}
		catch (IOException e)
		{
			this.connection.connectionError(e);
		}
	}
}