package protocol;

import java.io.OutputStream;

public class BitfieldMessage extends Message {

	private byte[] bitfield;
	
	public BitfieldMessage(byte[] bitfield) {
		this.bitfield = bitfield;
	}
	
	@Override
	public void sendMessage(OutputStream out) {
		
	}

}
