package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import main.*;
import network.*;

public class ButtonPanel extends JPanel
{
	public ButtonPanel()
	{
		this.setLayout(new GridLayout(1, 0, 5, 5));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.add(new JButton("Add Torrent"));
		this.add(new JButton("Remove Selected Torrent"));
		JButton manualConnect = new JButton("Manually Connect");
		this.add(manualConnect);
		
		manualConnect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String port = JOptionPane.showInputDialog("Enter peer port:");
				String peerId = JOptionPane.showInputDialog("Enter peer ID:");
				PeerInfo test = new PeerInfo(peerId.getBytes(), "127.0.0.1", Integer.parseInt(port));
				
				Torrent torrent = TorrentManager.getInstance().getTorrents().get(0);
				Connection connection = new Connection(torrent.getPeerManager(), test);
				torrent.getPeerManager().addConnection(connection);
				LoggingPanel.log("Successfully connected to peer!  ID = " + peerId);
			}
		});
	}
}