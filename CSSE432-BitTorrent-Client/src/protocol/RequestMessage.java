package protocol;

import java.io.IOException;
import java.io.OutputStream;

public class RequestMessage extends Message {

	public int index;
	public int begin;
	public int length;
	
	public RequestMessage(int index, int begin, int length) {
		this.index = index;
		this.begin = begin;
		this.length = length;
	}

	private byte[] makePayload() {
		byte[] indexByteArr = intToByteArray(index, 4);
		byte[] beginByteArr = intToByteArray(begin, 4);
		byte[] lengthByteArr = intToByteArray(length, 4);
		byte[] payload = new byte[indexByteArr.length + beginByteArr.length + lengthByteArr.length];
		int payloadIndex = 0;
		for (int i = 0; i < 4; i++) {
			payload[payloadIndex] = indexByteArr[i];
			payloadIndex++;
		}
		for (int j = 0; j < 4; j++) {
			payload[payloadIndex] = beginByteArr[j];
			payloadIndex++;
		}
		for (int k = 0; k < 4; k++) {
			payload[payloadIndex] = lengthByteArr[k];
			payloadIndex++;
		}
		return payload;
	}
	
	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] payload = makePayload();
		out.write(intToByteArray(1 + payload.length, 4));
		out.write(6);
		out.write(payload);
	}
}
