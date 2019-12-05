package uk.org.merg.jfcu.cbus.cbusdefs;

public enum CbusProperties {
	MANUFACTURER(1),
	MINOR_VERSION(2),
	MODULEID(3),
	NUMEVENTS(4),
	NUMEV(5),
	NUM_NV(6),
	MAJOR_VERSION(7),
	FLAGS(8),
	PROCESSOR(9),
	INTERFACE(10),
	LOAD(11),
	PROC_MANU(15),
	MAX(16);
	
	private final int id;
	CbusProperties(int i) {id = i;}
	public int getValue() {return id;}
	public static CbusProperties find (int i) {
		for (CbusProperties e : CbusProperties.values()) {
			if (e.getValue()==i) return e;
		}
		return null;
	}
}
