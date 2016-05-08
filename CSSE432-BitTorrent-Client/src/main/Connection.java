package main;

import java.io.*;
import java.net.*;

public class Connection
{
	private boolean choked;
	private boolean interested;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	public Connection()
	{
		this.choked = true;
		this.interested = false;
		
		try
		{
			this.serverSocket = new ServerSocket();
			this.clientSocket = new Socket();
			//this.inputStream = this.serverSocket.accept();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}