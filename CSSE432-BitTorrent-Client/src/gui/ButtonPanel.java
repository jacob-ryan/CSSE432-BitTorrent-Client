package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class ButtonPanel extends JPanel
{
	public ButtonPanel()
	{
		this.setLayout(new GridLayout(1, 0, 5, 5));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.add(new JButton("Add Torrent"));
		this.add(new JButton("Remove Selected Torrent"));
	}
}