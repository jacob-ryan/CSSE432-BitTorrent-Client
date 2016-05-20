package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class HaveMessage extends Message {

	public int dwnldrIndex;
	
	public HaveMessage(int dwnldrIndex) {
		this.dwnldrIndex = dwnldrIndex;
	}

	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] payload = intToByteArray(dwnldrIndex, 4);
		byte[] message = new byte[payload.length+1];
		message[0] = 4;
		for(int i = 0; i < payload.length; i++) {
			message[i+1] = payload[i];
		}
		out.write(message.length);
		out.write(message);
	}


}
