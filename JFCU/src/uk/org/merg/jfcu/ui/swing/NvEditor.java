package uk.org.merg.jfcu.ui.swing;

import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import uk.org.merg.jfcu.controller.ModuleTypeDataCache;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleType;
import uk.org.merg.jfcu.modulemodel.NvGroup;

public class NvEditor {
	public static void doNVs(JFCUswing jfcu, Module module) {
		String typeName = module.getModuleTypeName()+"v"+module.getVersion();
		ModuleType moduleType = ModuleTypeDataCache.get(typeName);
		if (moduleType == null) {
			JOptionPane.showMessageDialog(jfcu, "Module of type "+typeName+" is not currently supported.", 
					"NVs for "+typeName, JOptionPane.OK_CANCEL_OPTION);
			return;
		}
		ScrollPane scroll = new ScrollPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scroll.setPreferredSize(new Dimension(600,400));
		for (NvGroup g : moduleType.getNvGroups()) {
			JPanel group = new JPanel();
			group.setBorder(BorderFactory.createTitledBorder(g.getName()));
			group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
/*			JPanel bytes = new JPanel();
			// count the number of rows
			int count = 0;
			for (NvByte nv : g.getNvs()) {
				count+= nv.getNvbits().size();
			}
			GridLayout gl = new GridLayout(count,4);
			bytes.setLayout(gl);
			for (NvByte nv : g.getNvs()) {
				for (NvBits nb : nv.getNvbits()) {
					bytes.add(new JLabel(""+nv.getId()));
					bytes.add(new JLabel(""+nb.getName()));
					bytes.add(new JLabel(""+nb.getNvType().getValue(module, nv.getId(), nb.getBitmask())));
					bytes.add(new JLabel(""+nb.getDescription()));
				}
			}
			group.add(bytes);
			panel.add(group);*/
		
			NvGroupListDataModel tm = new NvGroupListDataModel(g, module);
			JTable nvTable = new JTable(tm) {
				private static final long serialVersionUID = 1L;
				//  Determine editor to be used by row
	            public TableCellEditor getCellEditor(int row, int column)
	            {
					return tm.getCellEditor(row, column, super.getCellEditor());
				}
	        };
			
			nvTable.getColumnModel().getColumn(NvGroupListDataModel.NV_NUMBER).setPreferredWidth(10); 
			nvTable.getColumnModel().getColumn(NvGroupListDataModel.NV_NAME).setPreferredWidth(50); 
			nvTable.getColumnModel().getColumn(NvGroupListDataModel.NV_VALUE).setPreferredWidth(100); 
			nvTable.getColumnModel().getColumn(NvGroupListDataModel.NV_DESCRIPTION).setPreferredWidth(200); 

			group.add(nvTable.getTableHeader());
			group.add(nvTable);
			panel.add(group);
		}
		scroll.add(panel);
		
		JOptionPane.showMessageDialog(jfcu, scroll, module.getName()+"("+typeName+")", JOptionPane.PLAIN_MESSAGE, null);
	}
}
