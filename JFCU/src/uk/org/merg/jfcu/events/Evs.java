package uk.org.merg.jfcu.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Evs {
	private Map<Integer, Integer> evs;
	
	public Evs() {
		evs = new HashMap<Integer, Integer>();
	}
	
	public void merge(Map<Integer, ByteOp> ops) {
		// get the set of EVs to be changed
		Set<Integer> evNos = evs.keySet();
		evNos.addAll(ops.keySet());
		
		for (int k : evNos) {
			int existing = 0;
			if (evs.containsKey(k)) {
				existing = evs.get(k);
			}
			if (ops.containsKey(k)) {
				ByteOp bo = ops.get(k);
				for (int b=0; b<8; b++) {
					switch (bo.getBit(k)) {
					case SET:
						existing |= (1<<b);
						break;
					case CLEAR:
						existing &= ~(1<<b);
						break;
					case UNCHANGED:
					}
				}
				evs.put(k, existing);
			}
		}
	}
}
