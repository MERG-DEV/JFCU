package uk.org.merg.jfcu.cbus;

import javax.swing.text.BadLocationException;

import co.uk.ccmr.cbus.CbusReceiveListener;
import co.uk.ccmr.cbus.sniffer.CbusEvent;
import co.uk.ccmr.cbus.sniffer.Opc;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MinPri;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MjPri;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.layoutmodel.Module;

public class ResponseHandler implements CbusReceiveListener {
//	private final static int PNN_MANUFACTURER = 2;
	private final static int PNN_MODULE_ID = 3;
//	private final static int PNN_FLAGS = 4;

	@Override
	public void receiveMessage(CbusEvent ce) {
		Module m;
		int nn;
		CbusEvent msg;
		
		System.out.println("GOT A MESSAGE "+ce.getOpc());
		try {
			Globals.log.insertString(0, "< "+ce+"\n", Globals.greenAset);
			Globals.log.insertString(0, "< "+ce.dump(16)+"\n", Globals.greenAset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		if (ce.getOpc() == Opc.PNN) {
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
			m.getParams().put(CbusProperties.MODULEID.getValue(), new SimpleIntegerProperty(ce.getData(PNN_MODULE_ID)));		// module ID in data 3
			Platform.runLater(new ModuleRunner(m){
				@Override
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
		}
		
		switch (ce.getOpc()) {
		case PARAN:
			// response to RQNPN
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) return;
			int paranNo = ce.getData(2);
			int paranVal = ce.getData(3);
			System.out.println("GOT No="+paranNo+" Val="+paranVal);
			Platform.runLater(new ModuleRunner(m){
				@Override
				public void run() {
					m.getParams().get(paranNo).setValue(paranVal);
				}});
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
			int nvNo = ce.getData(2);
			byte nvVal = (byte) ce.getData(3);
			Platform.runLater(new ModuleRunner(m){
				@Override
				public void run() {
					System.out.println("Got an NV "+nvNo+"="+nvVal);
					m.getNvs().put(nvNo, new SimpleIntegerProperty(nvVal));
					System.out.println("Saved NV "+nvNo+" as "+nvVal+" for "+m.getNodeNumber());
					System.out.println("m="+m);
				}});
			
			// request next NV
			if (nvNo < m.getParams().get(CbusProperties.NUM_NV).intValue()) {
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

	@Override
	public void receiveString(String input) {
		
	}
	
	public abstract class ModuleRunner implements Runnable {
		protected final Module m;
		ModuleRunner(Module m) {
			this.m = m;
		}
	}

}
