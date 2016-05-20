package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class BitfieldMessage extends Message {

	public byte[] bitfield;
	
	public BitfieldMessage(byte[] bitfield) {
		this.bitfield = bitfield;
	}
	
	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] messageType = new byte[1];
		messageType[0] = 5;
		out.write(messageType.length + this.bitfield.length);
		out.write(messageType);
		out.write(this.bitfield);
	}

}
