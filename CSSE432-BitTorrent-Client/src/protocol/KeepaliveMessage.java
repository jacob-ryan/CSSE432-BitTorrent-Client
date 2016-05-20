package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class KeepaliveMessage extends Message {

	@Override
	public void sendMessage(OutputStream out) throws IOException {
		out.write(intToByteArray(0, 4));
	}

}
