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
		m.getParams().put(CbusProperties.MODULEID.getValue(),32);
		m.getParams().put(CbusProperties.MAJOR_VERSION.getValue(),1);
		m.getParams().put(CbusProperties.MINOR_VERSION.getValue(),(int)'d');
		m.getParams().put(CbusProperties.NUM_NV.getValue(),127);	
		m.getParams().put(CbusProperties.MANUFACTURER.getValue(),165);
		m.getParams().put(CbusProperties.PROC_MANU.getValue(),1);
		m.getParams().put(CbusProperties.PROCESSOR.getValue(),15);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("LC panel");
		m.setNodeNumber((short) 1112);
		m.setCanid(2);
		m.getParams().put(CbusProperties.MODULEID.getValue(),32);
		m.getParams().put(CbusProperties.MAJOR_VERSION.getValue(),1);
		m.getParams().put(CbusProperties.MINOR_VERSION.getValue(),(int)'d');
		m.getParams().put(CbusProperties.NUM_NV.getValue(),127);	
		m.getParams().put(CbusProperties.MANUFACTURER.getValue(),165);
		m.getParams().put(CbusProperties.PROC_MANU.getValue(),1);
		m.getParams().put(CbusProperties.PROCESSOR.getValue(),13);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("Test PAN");
		m.setNodeNumber((short) 1113);
		m.setCanid(3);
		m.getParams().put(CbusProperties.MODULEID.getValue(),29);
		m.getParams().put(CbusProperties.MAJOR_VERSION.getValue(),4);
		m.getParams().put(CbusProperties.MINOR_VERSION.getValue(),(int)'h');
		m.getParams().put(CbusProperties.NUM_NV.getValue(),1);	
		m.getParams().put(CbusProperties.MANUFACTURER.getValue(),165);
		m.getParams().put(CbusProperties.PROC_MANU.getValue(),1);
		m.getParams().put(CbusProperties.PROCESSOR.getValue(),13);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("Test SERVO8C");
		m.setNodeNumber((short) 1114);
		m.setCanid(4);
		m.getParams().put(CbusProperties.MODULEID.getValue(),19);
		m.getParams().put(CbusProperties.MAJOR_VERSION.getValue(),1);
		m.getParams().put(CbusProperties.MINOR_VERSION.getValue(),(int)'y');
		m.getParams().put(CbusProperties.NUM_NV.getValue(),36);	
		m.getParams().put(CbusProperties.MANUFACTURER.getValue(),165);
		m.getParams().put(CbusProperties.PROC_MANU.getValue(),1);
		m.getParams().put(CbusProperties.PROCESSOR.getValue(),13);
		Globals.layout.getModules().add(m);
		
		System.out.println("constructed layout="+Globals.layout);
	}
}
