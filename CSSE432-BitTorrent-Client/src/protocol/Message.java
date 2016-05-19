package protocol;

import java.io.OutputStream;

public abstract class Message {
	public abstract void sendMessage(OutputStream out);
}
