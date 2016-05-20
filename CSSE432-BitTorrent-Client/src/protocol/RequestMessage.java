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
		byte[] beginByteArr = intToByteArray(index, 4);
		byte[] lengthByteArr = intToByteArray(index, 2 << 13);
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
		for (int k = 0; k < lengthByteArr.length; k++) {
			payload[payloadIndex] = lengthByteArr[k];
			payloadIndex++;
		}
		return payload;
	}
	
	@Override
	public void sendMessage(OutputStream out) throws IOException {
		byte[] payload = makePayload();
		byte[] messageType = new byte[1];
		messageType[0] = 6;
		out.write(messageType.length + payload.length);
		out.write(messageType);
		out.write(payload);
	}
}
