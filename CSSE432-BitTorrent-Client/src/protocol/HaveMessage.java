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
		byte[] messageType = new byte[1];
		messageType[0] = 4;
		out.write(messageType.length + payload.length);
		out.write(messageType);
		out.write(payload);
	}


}
