package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class BottomPanel extends JPanel
{
	public BottomPanel()
	{
		this.setLayout(new GridLayout(1, 0, 10, 10));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		this.add(new TorrentStatusPanel());
		this.add(new LoggingPanel());
	}
}