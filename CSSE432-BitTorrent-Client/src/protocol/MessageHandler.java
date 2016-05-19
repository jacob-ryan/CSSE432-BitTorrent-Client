package protocol;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class MessageHandler
{
	public static void writeHandshake(OutputStream output, byte[] infoHash, byte[] peerId) throws IOException
	{
		output.write(19);
		byte[] header = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);
		output.write(header);
		byte[] reserved = { 0, 0, 0, 0, 0, 0, 0, 0 };
		output.write(reserved);
		output.write(infoHash);
		output.write(peerId);
	}
	
	public static boolean verifyHandshake(InputStream input, byte[] infoHash, byte[] peerId) throws IOException
	{
		int length = input.read();
		if (length != 19)
		{
			return false;
		}
		
		byte[] testHeader = new byte[19];
		input.read(testHeader);
		byte[] header = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);
		if (!Arrays.equals(header, testHeader))
		{
			return false;
		}
		
		byte[] reserved = new byte[8];
		input.read(reserved);
		// Don't care about contents of reserved/extension bytes.
		
		byte[] testInfoHash = new byte[20];
		input.read(testInfoHash);
		if (!Arrays.equals(infoHash, testInfoHash))
		{
			return false;
		}
		
		byte[] testPeerId = new byte[20];
		input.read(testPeerId);
		if (!Arrays.equals(peerId, testPeerId))
		{
			return false;
		}
		
		return true;
	}
	
	public static byte[] readHandshake(InputStream input, byte[] infoHash) throws IOException
	{
		int length = input.read();
		if (length != 19)
		{
			return null;
		}
		
		byte[] testHeader = new byte[19];
		input.read(testHeader);
		byte[] header = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);
		if (!Arrays.equals(header, testHeader))
		{
			return null;
		}
		
		byte[] reserved = new byte[8];
		input.read(reserved);
		// Don't care about contents of reserved/extension bytes.
		
		byte[] testInfoHash = new byte[20];
		input.read(testInfoHash);
		if (!Arrays.equals(infoHash, testInfoHash))
		{
			return null;
		}
		
		byte[] peerId = new byte[20];
		input.read(peerId);
		
		return peerId;
	}
	
	public static Message readMessage(InputStream input) throws IOException {
		byte[] lengthPrefix = new byte[4];
		input.read(lengthPrefix);
		int messageLength = byteArrayToInt(lengthPrefix);
		if (messageLength == 0) {
			// Keep-alive
			return null;
		}
		byte[] messageType = new byte[1];
		input.read(messageType);
		byte[] payload = new byte[messageLength-1];
		input.read(payload);
		switch(messageType[0]) {
		case 0:
			return new ChokeMessage();
		case 1:
			return new UnchokeMessage();
		case 2:
			return new InterestedMessage();
		case 3:
			return new NotInterestedMessage();
		case 4:
			int dwnldrIndex = byteArrayToInt(payload);
			return new HaveMessage(dwnldrIndex);
		case 5:
			return new BitfieldMessage(payload);
		case 6:
			int rIndex = payload[0];
			int rBegin = payload[1];
			int rLength = byteArrayToInt(Arrays.copyOfRange(payload, 2, payload.length));
			return new RequestMessage(rIndex, rBegin, rLength);
		case 7:
			int index = payload[0];
			int begin = payload[1];
			byte[] piece = Arrays.copyOfRange(payload, 2, payload.length);
			return new PieceMessage(index, begin, piece);
		case 8:
			int cIndex = payload[0];
			int cBegin = payload[1];
			int cLength = byteArrayToInt(Arrays.copyOfRange(payload, 2, payload.length));
			return new CancelMessage(cIndex, cBegin, cLength);
		default:
			break;
		}
		return null;
	}
	
	private static int byteArrayToInt(byte[] b) 
	{
	    int value = 0;
	    for (int i = 0; i < 4; i++) {
	        int shift = (4 - 1 - i) * 8;
	        value += (b[i] & 0x000000FF) << shift;
	    }
	    return value;
	}
	
}




