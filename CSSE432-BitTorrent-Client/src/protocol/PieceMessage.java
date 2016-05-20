package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class PieceMessage extends Message {

	public int index;
	public int begin;
	public byte[] piece;
	
	public PieceMessage(int index, int begin, byte[] piece) {
		this.index = index;
		this.begin = begin;
		this.piece = piece;
	}
	
	private byte[] makePayload() {
		byte[] indexByteArr = intToByteArray(index, 4);
		byte[] beginByteArr = intToByteArray(index, 4);
		byte[] payload = new byte[indexByteArr.length + beginByteArr.length + this.piece.length];
		int payloadIndex = 0;
		for (int i = 0; i < 4; i++) {
			payload[payloadIndex] = indexByteArr[i];
			payloadIndex++;
		}
		for (int j = 0; j < 4; j++) {
			payload[payloadIndex] = beginByteArr[j];
			payloadIndex++;
		}
		for (int k = 0; k < this.piece.length; k++) {
			payload[payloadIndex] = this.piece[k];
			payloadIndex++;
		}
		return payload;
	}
	
	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] payload = makePayload();
		out.write(intToByteArray(1 + payload.length, 4));
		out.write(7);
		out.write(payload);
	}


}
