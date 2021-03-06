package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import main.*;
import network.*;
import protocol.BEncode;

public class ButtonPanel extends JPanel
{
	public ButtonPanel()
	{
		this.setLayout(new GridLayout(1, 0, 5, 5));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.addTorrentButton();
		
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
				PeerInfo test = new PeerInfo(peerId.getBytes(), JOptionPane.showInputDialog("Enter peer IP:"), Integer.parseInt(port));
				
				Torrent torrent = TorrentManager.getInstance().getTorrents().get(0);
				Connection connection;
				try {
					connection = new Connection(torrent.getPeerManager(), test);
					torrent.getPeerManager().addConnection(connection);
					LoggingPanel.log("Successfully connected to peer!  ID = " + peerId);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	private void addTorrentButton() {
		JButton addTorrent = new JButton("Add Torrent");
		this.add(addTorrent);
		final JFileChooser fc = new JFileChooser();
		addTorrent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int returnVal = fc.showOpenDialog(ButtonPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					String contents = "";
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(file));
						String text = null;
						while ((text = reader.readLine()) != null) {
							contents += text;
					    }
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
					    try {
					        if (reader != null) {
					            reader.close();
					        }
					    } catch (IOException e1) {
					    }
					}
					Object unparsedContents = BEncode.unparse(contents);
					if(unparsedContents instanceof HashMap<?,?>) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> metainfo = (HashMap<String, Object>) unparsedContents;
						@SuppressWarnings("unchecked")
						HashMap<String, Object> infoDict = (HashMap<String, Object>) metainfo.get("info");
						String torrName = (String) infoDict.get("name");
						int pieceLength = (Integer) infoDict.get("piece length");
						Torrent torr = new Torrent(torrName, pieceLength);
						@SuppressWarnings("unchecked")
						List<String> path = (List<String>) infoDict.get("path");
						String filename = "/";
						for(String dir : path) {
							filename += dir + "/";
						}
						if(filename.length() == 1) {
							System.err.println("Error with path.");
							return;
						}
						long length = (long) infoDict.get("length");
						torr.setFile(filename, length);
						TorrentManager.getInstance().addTorrent(torr);
					} else {
						System.err.println("Could not parse file.");
					}
				}
			}
		});
	}
}