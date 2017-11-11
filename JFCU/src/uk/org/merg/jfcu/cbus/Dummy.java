package uk.org.merg.jfcu.cbus;

import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.layoutmodel.Layout;
import uk.org.merg.jfcu.layoutmodel.Module;

public class Dummy {
	
	public static void buildDummyLayout() {
		Globals.layout = new Layout();
		Globals.layout.setDescription("Level Crossing Demo");
		Globals.layout.setVersion(1);
		
		Module m = new Module();
		m.setName("LC control");
		m.setNodeNumber((short) 1111);
		m.setCanid(1);
		m.getParams().get(CbusProperties.MODULEID.getValue()).setValue(32);
		m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).setValue(1);
		m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).setValue((byte)'d');
		m.getParams().get(CbusProperties.NUM_NV.getValue()).setValue(127);	
		m.getParams().get(CbusProperties.MANUFACTURER.getValue()).setValue(165);
		m.getParams().get(CbusProperties.PROC_MANU.getValue()).setValue(1);
		m.getParams().get(CbusProperties.PROCESSOR.getValue()).setValue(15);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("LC panel");
		m.setNodeNumber((short) 1112);
		m.setCanid(2);
		m.getParams().get(CbusProperties.MODULEID.getValue()).setValue(32);
		m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).setValue(1);
		m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).setValue((byte)'d');
		m.getParams().get(CbusProperties.NUM_NV.getValue()).setValue(127);	
		m.getParams().get(CbusProperties.MANUFACTURER.getValue()).setValue(165);
		m.getParams().get(CbusProperties.PROC_MANU.getValue()).setValue(1);
		m.getParams().get(CbusProperties.PROCESSOR.getValue()).setValue(13);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("Test PAN");
		m.setNodeNumber((short) 1113);
		m.setCanid(3);
		m.getParams().get(CbusProperties.MODULEID.getValue()).setValue(29);
		m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).setValue(4);
		m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).setValue((byte)'h');
		m.getParams().get(CbusProperties.NUM_NV.getValue()).setValue(1);	
		m.getParams().get(CbusProperties.MANUFACTURER.getValue()).setValue(165);
		m.getParams().get(CbusProperties.PROC_MANU.getValue()).setValue(1);
		m.getParams().get(CbusProperties.PROCESSOR.getValue()).setValue(13);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("Test SERVO8C");
		m.setNodeNumber((short) 1114);
		m.setCanid(4);
		m.getParams().get(CbusProperties.MODULEID.getValue()).setValue(19);
		m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).setValue(1);
		m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).setValue((byte)'y');
		m.getParams().get(CbusProperties.NUM_NV.getValue()).setValue(36);	
		m.getParams().get(CbusProperties.MANUFACTURER.getValue()).setValue(165);
		m.getParams().get(CbusProperties.PROC_MANU.getValue()).setValue(1);
		m.getParams().get(CbusProperties.PROCESSOR.getValue()).setValue(13);
		Globals.layout.getModules().add(m);
		
		System.out.println("constructed layout="+Globals.layout);
	}
}
