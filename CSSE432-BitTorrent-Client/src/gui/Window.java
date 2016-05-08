package gui;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.*;

public class Window extends JFrame
{
	private static final long serialVersionUID = 7446192599263749847L;

	public Window()
	{
		setWindowsLaF();
		
		this.setTitle("DMCA BitTorrent Client (By Jacob, Prithvi, and Matt)");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(1, 1));
		this.add(new MainPanel());
		
		this.setVisible(true);
	}
	
	private void setWindowsLaF()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setUIFont(new FontUIResource("Segoe UI", Font.PLAIN, 14));
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}
	
	private void setUIFont(FontUIResource f)
	{
		Enumeration<?> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements())
		{
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof javax.swing.plaf.FontUIResource)
			{
				UIManager.put(key, f);
			}
		}
	}
}