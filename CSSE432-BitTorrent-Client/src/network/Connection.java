package network;

import java.io.*;
import java.net.*;

import gui.*;
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
	private byte[] remoteBitfield;
	private ConnectionWriter connectionWriter;
	private ConnectionReader connectionReader;
	
	private Connection(PeerManager peerManager)
	{
		this.peerManager = peerManager;
		this.choked = true;
		this.interested = false;
	}
	
	public Connection(PeerManager peerManager, PeerInfo peerInfo) throws IOException
	{
		this(peerManager);
		this.peerInfo = peerInfo;
		
		this.socket = new Socket(this.peerInfo.ip, this.peerInfo.port);
		this.inputStream = this.socket.getInputStream();
		this.outputStream = this.socket.getOutputStream();
		
		byte[] infoHash = this.peerManager.getTorrent().getInfoHash();
		HandshakeMessage.writeHandshake(this.outputStream, infoHash, this.peerManager.getPeerId());
		
		if (!HandshakeMessage.verifyHandshake(this.inputStream, infoHash, this.peerInfo.id))
		{
			// Invalid handshake, forcefully terminate connection.
			this.socket.close();
			throw new IOException("Invalid handshake received from remote peer.");
		}
		
		finishInit();
	}
	
	public Connection(PeerManager peerManager, Socket socket) throws IOException
	{
		this(peerManager);
		this.peerInfo = new PeerInfo(null, socket.getInetAddress().getHostAddress(), socket.getPort());
		
		this.socket = socket;
		this.inputStream = this.socket.getInputStream();
		this.outputStream = this.socket.getOutputStream();
		
		byte[] infoHash = this.peerManager.getTorrent().getInfoHash();
		this.peerInfo.id = HandshakeMessage.readHandshake(this.inputStream, infoHash);
		if (this.peerInfo.id == null)
		{
			// Invalid handshake, forcefully terminate connection.
			this.socket.close();
			throw new IOException("Invalid handshake received from remote peer.");
		}
		
		HandshakeMessage.writeHandshake(this.outputStream, infoHash, this.peerManager.getPeerId());
		
		finishInit();
	}
	
	public PeerManager getPeerManager()
	{
		return this.peerManager;
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
	
	public boolean getChoked()
	{
		return this.choked;
	}
	
	public void setChoked(boolean choked)
	{
		this.choked = choked;
	}
	
	public boolean getInterested()
	{
		return this.interested;
	}
	
	public void setInterested(boolean interested)
	{
		this.interested = interested;
	}
	
	public byte[] getPieceBitfield()
	{
		return this.remoteBitfield;
	}
	
	public void setPieceBitfield(byte[] bitfield)
	{
		this.remoteBitfield = bitfield;
	}
	
	public ConnectionWriter getConnectionWriter()
	{
		return this.connectionWriter;
	}
	
	public ConnectionReader getConnectionReader()
	{
		return this.connectionReader;
	}
	
	public void connectionError(IOException e)
	{
		LoggingPanel.log("[Connection] An error occurred, closing connection: " + e.toString());
		this.peerManager.removeConnection(this);
		try
		{
			this.socket.close();
		}
		catch (IOException ee)
		{
			ee.printStackTrace();
		}
	}
	
	private void finishInit()
	{
		this.connectionWriter = new ConnectionWriter(this);
		this.connectionReader = new ConnectionReader(this);
	}
}