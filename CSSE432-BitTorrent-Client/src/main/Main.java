package main;

import gui.*;

public class Main
{
	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		new Window();
		LoggingPanel.log("Created GUI window...");
		
		Torrent torrent = new Torrent("Foo Bar", 256 * 1024);
		TorrentManager.getInstance().addTorrent(torrent);
	}
}