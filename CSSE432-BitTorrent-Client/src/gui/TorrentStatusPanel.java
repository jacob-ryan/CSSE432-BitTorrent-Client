package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class TorrentStatusPanel extends JPanel
{
	public TorrentStatusPanel()
	{
		this.setLayout(new GridLayout(1, 1));
		
		this.add(new JLabel("Torrent-specific details go here..."));
	}
}