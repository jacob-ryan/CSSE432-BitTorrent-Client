package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class ChokeMessage extends Message {

	@Override
	public void sendMessage(OutputStream out) throws IOException {
		out.write(intToByteArray(1, 4));
		out.write(0);
	}

	
}
