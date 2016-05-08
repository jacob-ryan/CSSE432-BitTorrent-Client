package gui;

import java.awt.*;

import javax.swing.*;

public class TopPanel extends JPanel
{
	private static final long serialVersionUID = -4696796014681020374L;

	public TopPanel()
	{
		this.setLayout(new BorderLayout());
		
		this.add(new ButtonPanel(), BorderLayout.PAGE_START);
		JScrollPane torrentList = new JScrollPane(new TorrentList());
		this.add(torrentList, BorderLayout.CENTER);
	}
}