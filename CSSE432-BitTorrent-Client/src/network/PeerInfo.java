package network;

public class PeerInfo
{
	public byte[] id;
	public String ip;
	public int port;
	
	public PeerInfo(byte[] id, String ip, int port)
	{
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
	
	public String toString()
	{
		return "[PeerInfo] IP=" + this.ip + ", port=" + this.port + ", ID=" + new String(this.id);
	}
}