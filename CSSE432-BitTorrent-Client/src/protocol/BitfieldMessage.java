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
		out.write(intToByteArray(1 + this.bitfield.length, 4));
		out.write(5);
		out.write(this.bitfield);
	}

}
