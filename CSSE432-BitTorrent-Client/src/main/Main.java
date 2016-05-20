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
		
		Torrent torrent = new Torrent("Testing.txt", Torrent.PIECE_LENGTH);
		torrent.setFile("Testing.txt", 15);
		//torrent.markSeeder();
		TorrentManager.getInstance().addTorrent(torrent);
	}
}