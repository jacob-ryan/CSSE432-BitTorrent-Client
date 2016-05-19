package protocol;

import java.io.OutputStream;

public class CancelMessage extends Message {

	private int index;
	private int begin;
	private int length;
	
	public CancelMessage(int index, int begin, int length) {
		this.index = index;
		this.begin = begin;
		this.length = length;
	}

	@Override
	public void sendMessage(OutputStream out) {

	}

}
