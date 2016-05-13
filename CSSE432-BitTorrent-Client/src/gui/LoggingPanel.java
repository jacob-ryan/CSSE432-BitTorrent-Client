package gui;

import java.awt.*;

import javax.swing.*;

public class LoggingPanel extends JPanel
{
	private static JTextArea logArea;
	
	public LoggingPanel()
	{
		this.setLayout(new GridLayout(1, 1));
		
		LoggingPanel.logArea = new JTextArea();
		this.add(new JScrollPane(LoggingPanel.logArea));
	}
	
	public static synchronized void log(String message)
	{
		String text = LoggingPanel.logArea.getText();
		text += message + "\n";
		LoggingPanel.logArea.setText(text);
	}
}