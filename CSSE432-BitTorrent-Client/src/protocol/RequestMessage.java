package protocol;

import java.io.OutputStream;

public class RequestMessage extends Message {

	private int index;
	private int begin;
	private int length;
	
	public RequestMessage(int index, int begin, int length) {
		this.index = index;
		this.begin = begin;
		this.length = length;
	}

	@Override
	public void sendMessage(OutputStream out) {

	}
}
