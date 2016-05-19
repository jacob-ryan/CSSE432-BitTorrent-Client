package protocol;

import java.io.OutputStream;

public class HaveMessage extends Message {

	private int dwnldrIndex;
	
	public HaveMessage(int dwnldrIndex) {
		this.dwnldrIndex = dwnldrIndex;
	}

	@Override
	public void sendMessage(OutputStream out) {

	}


}
