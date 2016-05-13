package gui;

import java.awt.*;

import javax.swing.*;

public class TorrentList extends JTable
{
	public TorrentList()
	{
		super(TorrentListModel.getInstance());
		TorrentListModel.getInstance().setTable(this);
		
		this.setFillsViewportHeight(true);
		this.setRowHeight(30);
		this.setGridColor(new Color(220, 220, 220));
	}
}