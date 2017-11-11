package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

public class ProcessorObservable implements ObservableStringValue {
	private ObservableNumberValue manu;
	private ObservableNumberValue proc;
	private List<ChangeListener<? super String>> listeners;
	
	private final static int CPUM_MICROCHIP        =  1;
	private final static int CPUM_ATMEL            =  2;
	private final static int CPUM_ARM              =  3;    
	    
	// Microchip Processor type codes (identifies to FCU for bootload compatibility)

	private final static int P18F2480	=	1;
	private final static int P18F4480	=	2;
	private final static int P18F2580	=	3;
	private final static int P18F4580	=	4;
	private final static int P18F2585	=	5;
	private final static int P18F4585	=	6;
	private final static int P18F2680	=	7;
	private final static int P18F4680	=	8;
	private final static int P18F2682	=	9;
	private final static int P18F4682	=	10;
	private final static int P18F2685	=	11;
	private final static int P18F4685	=	12;

	private final static int P18F25K80	=	13;
	private final static int P18F45K80	=	14;
	private final static int P18F26K80	=	15;
	private final static int P18F46K80	=	16;
	private final static int P18F65K80	=	17;
	private final static int P18F66K80	=	18;

	private final static int P32MX534F064  = 30;
	private final static int P32MX564F064  = 31;
	private final static int P32MX564F128  = 32;
	private final static int P32MX575F256  = 33;
	private final static int P32MX575F512  = 34;
	private final static int P32MX764F128  = 35;
	private final static int P32MX775F256  = 36;
	private final static int P32MX775F512  = 37;
	private final static int P32MX795F512  = 38;

	// ARM Processor type codes (identifies to FCU for bootload compatibility)

	private final static int ARM1176JZF_S  = 1;   // As used in Raspberry Pi
	private final static int ARMCortex_A7  = 2;   // As Used in Raspberry Pi 2
	private final static int ARMCortex_A53 = 3;   // As used in Raspberry Pi 3
	
	
	
	public ProcessorObservable(ObservableNumberValue manu, ObservableNumberValue proc) {
		this.manu = manu;
		this.proc = proc;
		listeners = new ArrayList<ChangeListener<? super String>>();
		manu.addListener(new ManuChangeListener(this));
		proc.addListener(new ProcChangeListener(this));
	}

	@Override
	public String get() {
		return lookup(manu.getValue().intValue(), proc.getValue().intValue());
	}

	/**
	 * TODO replace this with an XML file
	 * @param m
	 * @param p
	 * @return
	 */
	private String lookup(int m, int p) {
		switch(m) {
			case CPUM_MICROCHIP:	// micro
				switch (p) {
				case P18F2480:	return "PIC18F2480";
				case P18F4480:	return "PIC18F4480";
				case P18F2580:	return "PIC18F2580";
				case P18F4580:	return "PIC18F4580";
				case P18F2585:	return "PIC18F2585";
				case P18F4585:	return "PIC18F4585";
				case P18F2680:	return "PIC18F2680";
				case P18F4680:	return "PIC18F4680";
				case P18F2682:	return "PIC18F2682";
				case P18F4682:	return "PIC18F4682";
				case P18F2685:	return "PIC18F2685";
				case P18F4685:	return "PIC18F4685";

				case P18F25K80:	return "PIC18F25K80";
				case P18F45K80:	return "PIC18F45K80";
				case P18F26K80:	return "PIC18F26K80";
				case P18F46K80:	return "PIC18F46K80";
				case P18F65K80:	return "PIC18F65K80";
				case P18F66K80:	return "PIC18F66K80";

				case P32MX534F064:	return "PIC32MX534F064";
				case P32MX564F064:	return "PIC32MX564F064";
				case P32MX564F128:	return "PIC32MX564F128";
				case P32MX575F256:	return "PIC32MX575F256";
				case P32MX575F512:	return "PIC32MX575F512";
				case P32MX764F128:	return "PIC32MX764F128";
				case P32MX775F256:	return "PIC32MX775F256";
				case P32MX775F512:	return "PIC32MX775F512";
				case P32MX795F512:	return "PIC32MX795F512";
				default: return "Unknown Proc:"+p;
				}
			case CPUM_ATMEL:
				switch (p){
				default: return "Unknown Proc:"+p;
				}
			case CPUM_ARM:
				switch (p) {
				case ARM1176JZF_S:	return "ARM1176JZF_S";
				case ARMCortex_A7:	return "ARMCortex_A7";
				case ARMCortex_A53:	return "ARMCortex_A53";
				default: return "Unknown Proc:"+p;
				}
			default: return "Unknown Manu:"+m;
		}
		
		// Processor manufacturer codes


		
	}

	@Override
	public void addListener(ChangeListener<? super String> l) {
		listeners.add(l);
	}

	@Override
	public String getValue() {
		return lookup(manu.getValue().intValue(), proc.getValue().intValue());
	}

	@Override
	public void removeListener(ChangeListener<? super String> l) {
		listeners.remove(l);
	}

	@Override
	public void addListener(InvalidationListener arg0) {
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
	}
	
	private class ManuChangeListener implements ChangeListener<Number>{
		private ProcessorObservable po;
		public ManuChangeListener(ProcessorObservable po) {
			this.po = po;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(po, 
							lookup(oldValue.intValue(), proc.getValue().intValue()), 
							lookup(newValue.intValue(), proc.getValue().intValue()));
				}
			}
			
		}
	}
	private class ProcChangeListener implements ChangeListener<Number>{
		private ProcessorObservable po;
		public ProcChangeListener(ProcessorObservable po) {
			this.po = po;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(po, 
							lookup(manu.getValue().intValue(), oldValue.intValue()), 
							lookup(manu.getValue().intValue(), newValue.intValue()));
				}
			}
			
		}
	}

}
