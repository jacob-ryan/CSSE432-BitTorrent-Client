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
		out.write(intToByteArray(1 + payload.length, 4));
		out.write(4);
		out.write(payload);
	}


}
