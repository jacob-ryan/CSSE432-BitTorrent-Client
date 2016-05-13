package gui;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import main.*;

public class TorrentListModel extends AbstractTableModel
{
	private static TorrentListModel instance = new TorrentListModel();
	
	public static TorrentListModel getInstance()
	{
		return TorrentListModel.instance;
	}
	
	private List<String> columns;
	private JTable table;
	
	public TorrentListModel()
	{
		this.columns = new ArrayList<String>();
		this.columns.add(" Torrent Name: ");
		this.columns.add(" Size: ");
		this.columns.add(" Progress: ");
		this.table = null;
	}
	
	public void setTable(JTable table)
	{
		this.table = table;
	}

	@Override
	public int getColumnCount()
	{
		return this.columns.size();
	}

	@Override
	public int getRowCount()
	{
		return TorrentManager.getInstance().getTorrents().size();
	}
	
	public String getColumnName(int col)
	{
		return this.columns.get(col);
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Torrent torrent = TorrentManager.getInstance().getTorrents().get(row);
		if (col == 0)
		{
			return "  " + torrent.getName();
		}
		if (col == 1)
		{
			long size = 0;
			for (int i = 0; i < torrent.getNumFiles(); i += 1)
			{
				size += torrent.getFileLength(i);
			}
			return "  " + size + " B";
		}
		if (col == 2)
		{
			return "  " + "Unknown";
		}
		return "";
	}
	
	public void fireTableDataChanged()
	{
		int selectedRow = this.table.getSelectedRow();
		super.fireTableDataChanged();
		if (selectedRow >= 0)
		{
			this.table.setRowSelectionInterval(selectedRow, selectedRow);
		}
	}
}