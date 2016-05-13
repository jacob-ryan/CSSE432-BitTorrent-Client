package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class MainPanel extends JPanel
{
	private static final long serialVersionUID = -8438576029794021570L;

	public MainPanel()
	{
		this.setLayout(new GridLayout(1, 1));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new TopPanel(), new BottomPanel());
		splitPane.setResizeWeight(0.5);
		this.add(splitPane);
	}
}