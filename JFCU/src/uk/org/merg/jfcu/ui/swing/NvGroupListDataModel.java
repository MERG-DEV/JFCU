package uk.org.merg.jfcu.ui.swing;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.NvBits;
import uk.org.merg.jfcu.modulemodel.NvByte;
import uk.org.merg.jfcu.modulemodel.NvGroup;
import uk.org.merg.jfcu.modulemodel.NvItem;

public class NvGroupListDataModel implements TableModel {
	final static int NV_NUMBER = 0;
	final static int NV_NAME = 1;
	final static int NV_VALUE = 2;
	final static int NV_DESCRIPTION = 3;
	private NvGroup nvg;
	private Module module;
	
	public NvGroupListDataModel(NvGroup nvg, Module module) {
		this.nvg = nvg;
		this.module = module;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		switch(c) {
		case NV_NUMBER:
			return Integer.class;
		case NV_NAME:
			return String.class;
		case NV_VALUE:
			return String.class;
		case NV_DESCRIPTION:
			return String.class;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int c) {
		switch(c) {
		case NV_NUMBER:
			return "NV#";
		case NV_NAME:
			return "Name";
		case NV_VALUE:
			return "Value";
		case NV_DESCRIPTION:
			return "Description";
		}
		return null;
	}

	@Override
	public int getRowCount() {
		int count = 0;
		for (NvByte nv : nvg.getNvs()) {
			count += nv.getNvbits().size();
		}
		return count;
	}

	@Override
	public Object getValueAt(int r, int c) {
		NvBits nvb = null;
		int id=0;
		
		for (NvByte nv : nvg.getNvs()) {
			if (nv.getNvbits().size() > r) {
				id = nv.getId();
				nvb = nv.getNvbits().get(r);
				break;
			}
			r -= nv.getNvbits().size();
		}
		if (nvb == null) return null;
				
		switch(c) {
		case NV_NUMBER:
			return id;
		case NV_NAME:
			return nvb.getName();
		case NV_VALUE:
			return nvb.getNvType().getValue(module, id, nvb.getBitmask());
		case NV_DESCRIPTION:
			return nvb.getDescription();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		if (c == NV_VALUE) return true;
		return false;
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {

	}
	@Override
	public void removeTableModelListener(TableModelListener arg0) {

	}

	@Override
	public void setValueAt(Object arg0, int r, int c) {
		// TODO Auto-generated method stub
System.out.println("setValue r="+r+" c="+c+" val="+arg0);
	}

	public TableCellEditor getCellEditor(int r, int c, TableCellEditor defaultCellEditor) {
		if (c != NV_VALUE) return null;
		NvBits nvb = null;
		for (NvByte nv : nvg.getNvs()) {
			if (nv.getNvbits().size() > r) {
				nvb = nv.getNvbits().get(r);
				break;
			}
			r -= nv.getNvbits().size();
		}
		if (nvb == null) return null;
		
		if (nvb.getNvType().getNvChoice() != null) {
			JComboBox<String> cb = new JComboBox<String>();
			for (NvItem ni : nvb.getNvType().getNvChoice().getNvitems()) {
				cb.addItem(ni.getSetting());
			}
			return new DefaultCellEditor(cb);			
		}
		if (nvb.getNvType().getNvInteger() != null) {
			return new DefaultCellEditor(new JTextField());
		}
		return defaultCellEditor;
	}

}
