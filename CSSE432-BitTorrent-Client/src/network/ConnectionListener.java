package network;

import java.io.*;
import java.net.*;

import gui.*;

public class ConnectionListener extends Thread
{
	private PeerManager peerManager;
	private ServerSocket serverSocket;
	
	public ConnectionListener(PeerManager peerManager)
	{
		try
		{
			this.peerManager = peerManager;
			this.serverSocket = new ServerSocket(0, 10);
			this.start();
		}
		catch (IOException e)
		{
			System.out.println("[ConnectionListener] Could not create ServerSocket!");
			e.printStackTrace();
		}
	}
	
	public int getListeningPort()
	{
		return this.serverSocket.getLocalPort();
	}
	
	public void run()
	{
		LoggingPanel.log("[ConnectionListener] Listening on port: " + getListeningPort());
		while (true)
		{
			try
			{
				Socket clientSocket = this.serverSocket.accept();
				Connection connection = new Connection(this.peerManager, clientSocket);
				this.peerManager.addConnection(connection);
				LoggingPanel.log("[ConnectionListener] Accepted new connection from: " + connection.getPeerInfo().toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}