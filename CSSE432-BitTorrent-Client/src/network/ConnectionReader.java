package network;

import java.io.*;

import protocol.*;

public class ConnectionReader extends Thread
{
	private Connection connection;
	
	public ConnectionReader(Connection connection)
	{
		this.connection = connection;
		this.start();
	}
	
	public void run()
	{
		try
		{
			// Fully connected to peer (handshake sent/read and verified).
			// Must first read remote piece bitfield from the peer.
			
			Message m = Message.readMessage(this.connection.getInputStream());
			if (!(m instanceof BitfieldMessage))
			{
				throw new IOException("[Connection] Peer did not send initial BitfieldMessage.");
			}
			BitfieldMessage bm = (BitfieldMessage) m;
			this.connection.setPieceBitfield(bm.bitfield);
			
			while (true)
			{
				m = Message.readMessage(this.connection.getInputStream());
				if (m instanceof KeepaliveMessage)
				{
					// Do nothing.
				}
				if (m instanceof ChokeMessage)
				{
					this.connection.setChoked(true);
				}
				if (m instanceof UnchokeMessage)
				{
					this.connection.setChoked(false);
				}
				if (m instanceof InterestedMessage)
				{
					this.connection.setInterested(true);
				}
				if (m instanceof NotInterestedMessage)
				{
					this.connection.setInterested(false);
				}
				if (m instanceof HaveMessage)
				{
					HaveMessage hm = (HaveMessage) m;
					Bitfield.setPiece(this.connection.getPieceBitfield(), hm.dwnldrIndex);
				}
				if (m instanceof RequestMessage)
				{
					RequestMessage rm = (RequestMessage) m;
					// Queue sending of relevant data.
					this.connection.getConnectionWriter().handleRequestMessage(rm);
				}
				if (m instanceof PieceMessage)
				{
					PieceMessage pm = (PieceMessage) m;
					FileManager.writeData(this.connection.getTorrent().getFileName(), pm.index, pm.begin, pm.piece);
					
					// DON'T KNOW if we have whole piece yet, TODO???
					//Bitfield.setPiece(this.connection.getTorrent().getPieceBitfield(), pm.index);
					// Queue announcement that we have received a piece.
					//this.connection.getConnectionWriter().handlePieceMessage(pm);
				}
				if (m instanceof CancelMessage)
				{
					CancelMessage cm = (CancelMessage) m;
					// Dequeue sending of relevant data.
					this.connection.getConnectionWriter().handleCancelMessage(cm);
				}
			}
		}
		catch (IOException e)
		{
			this.connection.connectionError(e);
		}
	}
}