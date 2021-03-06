package protocol;

import main.*;

public class Bitfield
{
	public static byte[] make(Torrent torrent)
	{
		int numPieces = torrent.getNumberOfPieces();
		int length = (int) Math.ceil(numPieces / 8.0);
		return new byte[length];
	}
	
	public static boolean getPiece(byte[] bitfield, int piece)
	{
		int index = piece / 8;
		return (bitfield[index] & 0xFF) >> (piece % 8) != 0;
	}
	
	public static void setPiece(byte[] bitfield, int piece)
	{
		int index = piece / 8;
		bitfield[index] |= 1 << 8 >> (piece % 8);
	}
}