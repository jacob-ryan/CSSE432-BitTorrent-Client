package gui;

import javax.swing.*;

public class TorrentList extends JTable
{
	public TorrentList()
	{
		super(new Object[][] {}, new String[] { "Torrent Name", "Size", "Progress" });
		this.setFillsViewportHeight(true);
	}
}