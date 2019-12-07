package uk.org.merg.jfcu.events;

public class ByteOp {
	private BitOp[] bitOps;
	
	public ByteOp() {
		bitOps = new BitOp[8];
	}
	
	public BitOp getBit(int b) {
		if ((b <0) || (b > 7)) throw new IllegalArgumentException();
		return bitOps[b];
	}
	
	public void setBit(int b, BitOp op) {
		if ((b <0) || (b > 7)) throw new IllegalArgumentException();
		bitOps[b] = op;
	}
	
	public int asInt() {
		int ret = 0;
		for (int i=0; i<8; i++) {
			if (bitOps[i] == null) continue;
			switch (bitOps[i]) {
			case SET:
				ret |= (1<<i);
			case CLEAR:
			case UNCHANGED:
			}
		}
		return ret;
	}
}
