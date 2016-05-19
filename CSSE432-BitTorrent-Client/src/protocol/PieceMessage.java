package protocol;

import java.io.OutputStream;

public class PieceMessage extends Message {

	private int index;
	private int begin;
	private byte[] piece;
	
	public PieceMessage(int index, int begin, byte[] piece) {
		this.index = index;
		this.begin = begin;
		this.piece = piece;
	}
	
	@Override
	public void sendMessage(OutputStream out) {
		
	}


}
