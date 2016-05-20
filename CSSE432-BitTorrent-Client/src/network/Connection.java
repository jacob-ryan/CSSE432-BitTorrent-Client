package network;

import java.io.*;
import java.net.*;

import main.*;
import protocol.*;

public class Connection
{
	private PeerManager peerManager;
	private PeerInfo peerInfo;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private boolean choked;
	private boolean interested;
	private byte[] pieceBitfield;
	
	private Connection(PeerManager peerManager)
	{
		this.peerManager = peerManager;
		this.choked = true;
		this.interested = false;
	}
	
	public Connection(PeerManager peerManager, PeerInfo peerInfo)
	{
		this(peerManager);
		this.peerInfo = peerInfo;
		
		try
		{
			this.socket = new Socket(this.peerInfo.ip, this.peerInfo.port);
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
			
			byte[] infoHash = this.peerManager.getTorrent().getInfoHash();
			MessageHandler.writeHandshake(this.outputStream, infoHash, this.peerManager.getPeerId());
			
			if (!MessageHandler.verifyHandshake(this.inputStream, infoHash, this.peerInfo.id))
			{
				// Invalid handshake, forcefully terminate connection.
				this.socket.close();
				throw new IOException("Invalid handshake received from remote peer.");
			}
			
			finishInit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Connection(PeerManager peerManager, Socket socket)
	{
		this(peerManager);
		this.peerInfo = new PeerInfo(null, socket.getInetAddress().getHostAddress(), socket.getPort());
		
		try
		{
			this.socket = socket;
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
			
			byte[] infoHash = this.peerManager.getTorrent().getInfoHash();
			this.peerInfo.id = MessageHandler.readHandshake(this.inputStream, infoHash);
			if (this.peerInfo.id == null)
			{
				// Invalid handshake, forcefully terminate connection.
				this.socket.close();
				throw new IOException("Invalid handshake received from remote peer.");
			}
			
			MessageHandler.writeHandshake(this.outputStream, infoHash, this.peerManager.getPeerId());
			
			finishInit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public PeerInfo getPeerInfo()
	{
		return this.peerInfo;
	}
	
	public InputStream getInputStream()
	{
		return this.inputStream;
	}
	
	public OutputStream getOutputStream()
	{
		return this.outputStream;
	}
	
	public void setChoked(boolean choked)
	{
		this.choked = choked;
	}
	
	public void setInterested(boolean interested)
	{
		this.interested = interested;
	}
	
	public byte[] getPieceBitfield()
	{
		return this.pieceBitfield;
	}
	
	public void setPieceBitfield(byte[] bitfield)
	{
		this.pieceBitfield = bitfield;
	}
	
	public Torrent getTorrent()
	{
		return this.peerManager.getTorrent();
	}
	
	private void finishInit()
	{
		new ConnectionReader(this);
		new ConnectionWriter(this);
	}
}