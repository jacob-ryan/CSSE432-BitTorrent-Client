package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class InterestedMessage extends Message {
	
	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] message = new byte[1];
		message[0] = 2;
		out.write(message.length);
		out.write(message);
	}

}
