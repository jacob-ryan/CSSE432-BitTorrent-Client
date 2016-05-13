package gui;

public class GUIUpdater extends Thread
{
	public GUIUpdater()
	{
		start();
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(1000);
				
				TorrentListModel.getInstance().fireTableDataChanged();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}