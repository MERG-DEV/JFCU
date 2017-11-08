package uk.org.merg.jfcu.cbus;

import javax.swing.text.BadLocationException;

import co.uk.ccmr.cbus.CbusReceiveListener;
import co.uk.ccmr.cbus.sniffer.CbusEvent;
import co.uk.ccmr.cbus.sniffer.Opc;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MinPri;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MjPri;
import javafx.application.Platform;
import uk.org.merg.jfcu.layoutmodel.Module;

public class ResponseHandler implements CbusReceiveListener {
	private final static int PARAN_MANUFACTURER = 1;
	private final static int PARAN_MINOR_VERSION = 2;
	private final static int PARAN_MODULE_ID = 3;
	private final static int PARAN_NUM_EVENTS = 4;
	private final static int PARAN_NUM_EVS = 5;
	private final static int PARAN_NUM_NVS = 6;
	private final static int PARAN_MAJOR_VERSION = 7;
	
	private final static int PNN_MANUFACTURER = 2;
	private final static int PNN_MODULE_ID = 3;
	private final static int PNN_FLAGS = 4;

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
			m.setModuleTypeId(ce.getData(PNN_MODULE_ID));		// module ID in data 3
			m.setModuleTypeName(Globals.moduleTypeNames.find(m.getModuleTypeId()));
			System.out.println("MODULE TYPE ID="+m.getModuleTypeId());
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
			msg.setData(2, PARAN_MAJOR_VERSION);		// param 7 is the major version
			Comms.theDriver.queueForTransmit(msg);
			return;
		}
		
		switch (ce.getOpc()) {
		case PARAN:
			// response to RQNPN
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) {
				m = new Module();		
				m.setNodeNumber(nn);
				m.setCanid(ce.getCANID());
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						Globals.layout.getModules().add(this.m);
					}});
				
				// Send a RQNPN to get module id
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, PARAN_MODULE_ID);		// param 3 is module id
				Comms.theDriver.queueForTransmit(msg);
				return;
			}
			int paranNo = ce.getData(2);
			int paranVal = ce.getData(3);
			System.out.println("GOT No="+paranNo+" Val="+paranVal);
			Platform.runLater(new ModuleRunner(m){
				@Override
				public void run() {
					m.getParams().put(paranNo,  (byte)paranVal);
				}});
			switch (paranNo) {
			case PARAN_MODULE_ID:	//module id
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						m.setModuleTypeId(paranVal);
						m.setModuleTypeName(Globals.moduleTypeNames.find(m.getModuleTypeId()));
						System.out.println("MODULE TYPE ID="+m.getModuleTypeId());
						Globals.layout.getModules().add(m);
					}});
				
				
				// Send a RQNPN to get version
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, PARAN_MAJOR_VERSION);		// param 7 is the major version
				Comms.theDriver.queueForTransmit(msg);
				break;
			case PARAN_MAJOR_VERSION: // major version
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						m.setVersion(""+paranVal);
						System.out.println("GOT a MJ VERSION="+m.getVersion());
					}});
				
				// request the major version
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, PARAN_MINOR_VERSION);		// param 2 is the minor version
				Comms.theDriver.queueForTransmit(msg);
				break;
			case PARAN_MINOR_VERSION:	// minor version
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						m.setSubVersion(""+(char)paranVal);
						System.out.println("GOT a MN VERSION="+m.getSubVersion());
					}});
				
				// request the number of NVs
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, PARAN_NUM_NVS);		// param 6 is the number of NVs
				Comms.theDriver.queueForTransmit(msg);
				break;
			case PARAN_NUM_NVS:	// number of NVs
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						m.setNumNV(paranVal);
					}});
				break;
			}
			break;
		case NVANS:
			nn = ce.getNN();
			if (nn < 0) nn += 65536;
			m = Globals.layout.findModule(nn);
			if (m == null) {
				m = new Module();		
				m.setNodeNumber(nn);
				Platform.runLater(new ModuleRunner(m){
					@Override
					public void run() {
						Globals.layout.getModules().add(m);
					}});
				
				// Send a RQNPN to get module id
				msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, PARAN_MODULE_ID);		// param 3 is module id
				Comms.theDriver.queueForTransmit(msg);
				return;
			}
			int nvNo = ce.getData(2);
			byte nvVal = (byte) ce.getData(3);
			Platform.runLater(new ModuleRunner(m){
				@Override
				public void run() {
					System.out.println("Got an NV "+nvNo+"="+nvVal);
					m.getNvs().put(nvNo, nvVal);
					System.out.println("Saved NV "+nvNo+" as "+nvVal+" for "+m.getNodeNumber());
					System.out.println("m="+m);
				}});
			
			// request next NV
			if (nvNo < m.getNumNV()) {
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
