package uk.org.merg.jfcu.cbus;

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
		m.setModuleTypeName("CANMIO");
		m.setModuleTypeId(32);
		m.setVersion("1");
		m.setSubVersion("d");
		m.setNumNV(127);
		Globals.layout.getModules().add(m);
		
		m = new Module();
		m.setName("LC panel");
		m.setNodeNumber((short) 1112);
		m.setModuleTypeName("CANMIO");
		m.setModuleTypeId(32);
		m.setVersion("1");
		m.setSubVersion("d");
		m.setNumNV(127);
		Globals.layout.getModules().add(m);
		
		System.out.println("constructed layout="+Globals.layout);
	}
}
