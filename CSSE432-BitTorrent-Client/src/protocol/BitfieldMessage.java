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
		byte[] message = new byte[bitfield.length+1];
		message[0] = 5;
		for(int i = 0; i < bitfield.length; i++) {
			message[i+1] = bitfield[i];
		}
		out.write(message.length);
		out.write(message);
	}

}
