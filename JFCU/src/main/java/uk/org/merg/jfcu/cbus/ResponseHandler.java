package uk.org.merg.jfcu.cbus;

import javax.swing.text.BadLocationException;

import co.uk.ccmr.cbus.CbusReceiveListener;
import co.uk.ccmr.cbus.sniffer.CbusEvent;
import co.uk.ccmr.cbus.sniffer.Opc;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MinPri;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MjPri;
import javafx.application.Platform;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.layoutmodel.Event;
import uk.org.merg.jfcu.layoutmodel.Event.Etype;
import uk.org.merg.jfcu.layoutmodel.Module;

public class ResponseHandler implements CbusReceiveListener {
//	private final static int PNN_MANUFACTURER = 2;
	private final static int PNN_MODULE_ID = 3;
//	private final static int PNN_FLAGS = 4;

	public void receiveMessage(CbusEvent ce) {
		Module m;
		int nn;
		CbusEvent msg;
		Event evt;
		
		System.out.println("GOT A MESSAGE "+ce.getOpc());
		try {
			Globals.log.insertString(0, "< "+ce+"\n", Globals.greenAset);
			Globals.log.insertString(0, "< "+ce.dump(16)+"\n", Globals.greenAset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		switch (ce.getOpc()) {
		case PNN:
			// response to a QNN
			//Find or Create a module
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) {
				m = new Module();
				m.setNodeNumber(nn);
			}
			m.setCanid(ce.getCANID());
			m.getParams().put(CbusProperties.MODULEID.getValue(), ce.getData(PNN_MODULE_ID));		// module ID in data 3
			Platform.runLater(new ModuleRunner(m){
				public void run() {
					Globals.layout.getModules().add(this.m);
				}});
			
			
			// Send a RQNPN to get version
			msg = new CbusEvent();
			msg.setMinPri(MinPri.HIGH);
			msg.setMjPri(MjPri.HIGH);
			msg.setCANID(Globals.CANID);
			msg.setOpc(Opc.RQNPN);
			msg.setNN(m.getNodeNumber());
			msg.setData(2, CbusProperties.MAJOR_VERSION.getValue());		// param 7 is the major version
			Comms.theDriver.queueForTransmit(msg);
			return;
		case PARAN:
			// response to RQNPN
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) return;
			int paranNo = ce.getData(2);
			int paranVal = ce.getData(3);
			System.out.println("GOT No="+paranNo+" Val="+paranVal);
			Platform.runLater(new ModuleParamUpdater(m, paranNo, paranVal));
			switch (CbusProperties.find(paranNo)) {
			case MODULEID:	//Got module id
				// Send a RQNPN to get version
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.MAJOR_VERSION.getValue());		// Request param 7 is the major version
				Comms.theDriver.queueForTransmit(msg);
				break;
			case MAJOR_VERSION: // Got major version
				// request the minor version
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.MINOR_VERSION.getValue());		// Request param 2 is the minor version
				Comms.theDriver.queueForTransmit(msg);
				break;
			case MINOR_VERSION:	// Got minor version
				// request the number of NVs
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.NUM_NV.getValue());		// Request param 6 is the number of NVs
				Comms.theDriver.queueForTransmit(msg);
				break;
			case NUM_NV:	// Got number of NVs
				// request the Manufacturer
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.MANUFACTURER.getValue());		// Request param 1 is the Manufacturer
				Comms.theDriver.queueForTransmit(msg);
				break;
			case MANUFACTURER:	// Got Manufacturer
				// request the flags
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.FLAGS.getValue());		// Request flags
				Comms.theDriver.queueForTransmit(msg);
				break;
			case FLAGS:	// Got flags
				// request the number of Events
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.NUMEVENTS.getValue());		// Request number of Events
				Comms.theDriver.queueForTransmit(msg);
				break;
			case NUMEVENTS:	// Got Events
				// request the num EVs
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.NUMEV.getValue());		// Request Num EVs
				Comms.theDriver.queueForTransmit(msg);
				break;
			case NUMEV:	// Got EVs
				// request the processor
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.PROCESSOR.getValue());		// Request processor
				Comms.theDriver.queueForTransmit(msg);
				break;
			case PROCESSOR:	// Got Evnts
				// request the processor manufacturer
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.PROC_MANU.getValue());		// Request processor manufacturer
				Comms.theDriver.queueForTransmit(msg);
				break;
			case PROC_MANU:	// Got Evnts
				// done getting properties
				break;
			default:
				break;
			}
			break;
		case NVANS:
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) 	return;
			final int nvNo = ce.getData(2);
			final byte nvVal = (byte) ce.getData(3);
			Platform.runLater(new ModuleRunner(m){
				public void run() {
					System.out.println("Got an NV "+nvNo+"="+nvVal);
					m.getNvs().put(nvNo, (int)nvVal);
					System.out.println("Saved NV "+nvNo+" as "+nvVal+" for "+m.getNodeNumber());
					System.out.println("m="+m);
				}});
			
			// request next NV
			if (nvNo < m.getParams().get(CbusProperties.NUM_NV.getValue()).intValue()) {
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.NVRD);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, nvNo+1);	
				Comms.theDriver.queueForTransmit(msg);
			}
			break;
		case ACON:
		case ACON1:
		case ACON2:
		case ACON3:
			evt = new Event();
			evt.setNn(ce.getNN());
			evt.setEn(ce.getDN());
			evt.setLength(Etype.LONG);
			if (! Globals.layout.getEvents().contains(evt)) {
				Globals.layout.getEvents().add(evt);
			}
			break;
		case ASON:
		case ASON1:
		case ASON2:
		case ASON3:
			evt = new Event();
			evt.setNn(0);
			evt.setEn(ce.getDN());
			evt.setLength(Etype.SHORT);
			if (! Globals.layout.getEvents().contains(evt)) {
				Globals.layout.getEvents().add(evt);
			}
			break;
		
		case CMDERR:
			try {
				Globals.log.insertString(0, "CMDERR"+ce.getData(3)+"\n", Globals.redAset);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
	}

	public void receiveString(String input) {
		
	}
	
	public abstract class ModuleRunner implements Runnable {
		protected final Module m;
		ModuleRunner(Module m) {
			this.m = m;
		}
	}
	
	public class ModuleParamUpdater implements Runnable {
		protected final Module m;
		private int idx;
		private int val;
		ModuleParamUpdater(Module m, int idx, int val) {
			this.m = m;
			this.idx= idx;
			this.val = val;
		}
		public void run() {
			m.getParams().put(idx,val);
		}
	}

}
