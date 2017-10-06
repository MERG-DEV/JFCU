package uk.org.merg.jfcu.ui.swing;


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Module;

public class NodeListDataModel implements TableModel {
	private final static int MODULE_TYPE    = 0;
	private final static int MODULE_NAME    = 1;
	private final static int NODE_NUMBER    = 2;
	private final static int MODULE_ID      = 3;
	private final static int MODULE_VERSION = 4;
	private final static int MODULE_NUM_NV  = 5;

	@Override
	public Class<?> getColumnClass(int c) {
		switch (c) {
		case NODE_NUMBER: 	return Integer.class;
		case MODULE_TYPE: 	return String.class;
		case MODULE_ID:   	return Integer.class;
		case MODULE_NAME: 	return String.class;
		case MODULE_VERSION:return Integer.class;
		case MODULE_NUM_NV: return Integer.class;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int c) {
		switch (c) {
		case NODE_NUMBER:   return "Node Number";
		case MODULE_TYPE:   return "Module Type";
		case MODULE_ID:     return "Type ID";
		case MODULE_NAME:   return "Name";
		case MODULE_VERSION:return "Version";
		case MODULE_NUM_NV: return "Num NVs";
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return Globals.layout.getModules().size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		Module m = Globals.layout.getModules().get(r);
		switch(c) {
		case NODE_NUMBER:
			return m.getNodeNumber();
		case MODULE_TYPE:
			return m.getModuleTypeName();
		case MODULE_ID:
			return m.getModuleTypeId();
		case MODULE_NAME: 
			return m.getName();
		case MODULE_VERSION:
			return m.getVersion();
		case MODULE_NUM_NV:
				return m.getNumNV();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		if (c == MODULE_NAME) return true;
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		
	}

	@Override
	public void setValueAt(Object v, int r, int c) {
		Module m = Globals.layout.getModules().get(r);
		if (c == MODULE_NAME) {
			m.setName((String)v);
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
		
	}

}
