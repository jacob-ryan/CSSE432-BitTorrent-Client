package main;

import java.util.*;

public class TorrentManager
{
	private static TorrentManager instance = new TorrentManager();
	
	public static TorrentManager getInstance()
	{
		return TorrentManager.instance;
	}
	
	private List<Torrent> torrents;
	
	public TorrentManager()
	{
		this.torrents = new ArrayList<Torrent>();
	}
	
	public List<Torrent> getTorrents()
	{
		return this.torrents;
	}
	
	public void addTorrent(Torrent torrent)
	{
		this.torrents.add(torrent);
	}
}