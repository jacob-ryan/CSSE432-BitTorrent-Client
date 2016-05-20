package protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class Message {
	public abstract void sendMessage(OutputStream out) throws IOException;
	
	public static Message readMessage(InputStream input) throws IOException {
		byte[] lengthPrefix = new byte[4];
		input.read(lengthPrefix);
		int messageLength = byteArrayToInt(lengthPrefix);
		if (messageLength == 0) {
			// Keep-alive
			return new KeepaliveMessage();
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
			int rIndex = byteArrayToInt(Arrays.copyOfRange(payload, 0, 4));
			int rBegin = byteArrayToInt(Arrays.copyOfRange(payload, 4, 8));
			int rLength = byteArrayToInt(Arrays.copyOfRange(payload, 8, 12));
			return new RequestMessage(rIndex, rBegin, rLength);
		case 7:
			int index = byteArrayToInt(Arrays.copyOfRange(payload, 0, 4));
			int begin = byteArrayToInt(Arrays.copyOfRange(payload, 4, 8));
			byte[] piece = Arrays.copyOfRange(payload, 8, payload.length);
			return new PieceMessage(index, begin, piece);
		case 8:
			int cIndex = byteArrayToInt(Arrays.copyOfRange(payload, 0, 4));
			int cBegin = byteArrayToInt(Arrays.copyOfRange(payload, 4, 8));
			int cLength = byteArrayToInt(Arrays.copyOfRange(payload, 8, payload.length));
			return new CancelMessage(cIndex, cBegin, cLength);
		default:
			throw new IOException("Unknown Message Type");
		}
	}
	
	public static byte[] intToByteArray(int a, int arraySize)
	{
	    byte[] ret = new byte[arraySize];
	    for(int i = arraySize-1; i >= 0; i--) {
	    	ret[i] = (byte) ((a >> ((arraySize-i) * 8)) & 0xFF);
	    }
	    return ret;
	}
	
	public static int byteArrayToInt(byte[] b) 
	{
	    int value = 0;
	    for (int i = 0; i < 4; i++) {
	        int shift = (4 - 1 - i) * 8;
	        value += (b[i] & 0x000000FF) << shift;
	    }
	    return value;
	}
}
