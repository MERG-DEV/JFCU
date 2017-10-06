package uk.org.merg.jfcu.ui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import co.uk.cbusio.ui.LimitLinesDocumentListener;
import uk.org.merg.jfcu.cbus.Comms;
import uk.org.merg.jfcu.cbus.Dummy;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Layout;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleDefs;


public class JFCUswing extends JFrame implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5681558991282775552L;
	
	private JTextPane textPane;
	private ButtonGroup connectGroup;
	private Container connectMenu;
	private JFCUswing self = null;
	private String portText;
	private static JTable nodeList;
	
	public static void main(String [] args) {
		Globals.ui = Globals.UiChoice.SWING;
		Dummy.buildDummyLayout();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	JFCUswing fcu = new JFCUswing(args);
            	Comms.prepareCommsDriver(args);
        		ModuleDefs.loadModuleDefinitions();
                fcu.createAndShowGUI();
            }
        });
	}
	
	
	public JFCUswing(String [] args) {
		self = this;
		
		textPane = new JTextPane();
		Globals.init(textPane.getStyledDocument());
		Globals.log.addDocumentListener(new LimitLinesDocumentListener(Globals.MAX_LINES));
	}
	
	public void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menubar= new JMenuBar();
		JMenu menu;
		JMenuItem mi;
		
		menu = new JMenu("File");
		mi = new JMenuItem("Load from file ...");
		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "XML files", "xml");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(self);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	Globals.layout = Layout.load(chooser.getSelectedFile().getName());
			       JFCUswing.fireModuleChanged(null);
			    }
			}});
		
		  menu.add(mi);
		  mi = new JMenuItem("Save as file...");
		  mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "XML files", "xml");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(self);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	Globals.layout.save(chooser.getSelectedFile().getName());
				    }
				}});
		  menu.add(mi);
		  menu.addSeparator();
		  mi = new JMenuItem("Load from CBUS");
		  mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Globals.layout = new Layout();
				JFCUswing.fireModuleChanged(null);
				Globals.layout.loadFromCBUS();
			}	
		  });
		  menu.add(mi);
		  menu.addSeparator();
		  menu.add(new JMenuItem("Exit"));
		menubar.add(menu);
		connectMenu = new JMenu("Connect");
		  String[] portList;
          connectGroup = new ButtonGroup();
          portList = Comms.theDriver.getPortNames();
          if (portList.length > 0) portText=null;
          System.out.println("Portlist len="+portList.length);
          for (String p : portList) {
        	JRadioButtonMenuItem item = new JRadioButtonMenuItem(p);
        	item.addItemListener(this);
        	connectGroup.add(item);
        	connectMenu.add(item);
          }
          if ((portText != null) && (portText.length() > 0)) {
        	JRadioButtonMenuItem item = new JRadioButtonMenuItem(portText);
        	item.addItemListener(this);;
        	connectGroup.add(item);;
        	connectMenu.add(item);
          }
		  mi = new JMenuItem("Define Port");
	      mi.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent arg0) {
	        		portText = JOptionPane.showInputDialog("Enter port device name");
	        		if ((portText != null) && (portText.length() > 0)) {
	                	JRadioButtonMenuItem item = new JRadioButtonMenuItem(portText);
	                	item.addItemListener(self);
	                	connectGroup.add(item);;
	                	connectMenu.add(item);
	                }
	        	}
	      });
	      menu.add(mi);
		menubar.add(connectMenu);
		menu = new JMenu("Edit");
		  menu.add(new JMenuItem("edit1"));
		  menu.add(new JMenuItem("edit2"));
		  menu.add(new JMenuItem("edit3"));
		menubar.add(menu);
		menu = new JMenu("Options");
		  menu.add(new JMenuItem("options1"));
		  menu.add(new JMenuItem("options2"));
		  menu.add(new JMenuItem("options3"));
		menubar.add(menu);
		menu = new JMenu("Help");
		  menu.add(new JMenuItem("User Guide"));
		  menu.add(new JMenuItem("About"));
		menubar.add(menu);
		setJMenuBar(menubar);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		nodeList = new JTable(new NodeListDataModel());
		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem nvs = new JMenuItem("NVs");
        nvs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selection = nodeList.getSelectedRows();
                if ((selection != null) && (selection.length > 0)) {
                	NvEditor.doNVs(self, Globals.layout.getModules().get(selection[0]));
                }
            }


        });
        popupMenu.add(nvs);
        JMenuItem events = new JMenuItem("Events");
        events.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selection = nodeList.getSelectedRows();
                if ((selection != null) && (selection.length > 0)) {
                	// TODO
                }
            }


        });
        popupMenu.add(events);
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = nodeList.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), nodeList));
                        if (rowAtPoint > -1) {
                        	nodeList.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }
        });
        nodeList.setComponentPopupMenu(popupMenu);
		
		
	    JScrollPane scrollpane = new JScrollPane(nodeList);
		panel.add(scrollpane, BorderLayout.CENTER);
		
		JScrollPane logScroll = new JScrollPane(textPane);
		panel.add(logScroll, BorderLayout.EAST);
		pack();
		setVisible(true);
	}
	
	public static void fireModuleChanged(Module m) {
		// lazy for now just redo all
		nodeList.repaint();
	}
			
	
	/**
	 * Called when serial port is changed.
	 * If a serial port is currently open then close it. Open the new one selected.
	 * If there currently isn't a reader thread then create one and run it.
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		JRadioButtonMenuItem rb = (JRadioButtonMenuItem) event.getItem();
		Comms.portName.set(rb.getText());
		
		switch(event.getStateChange()) {
		case ItemEvent.SELECTED:
			try {
				Globals.log.insertString(0, "Opening port "+Comms.portName+"\n", Globals.redAset);
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			Comms.connect(Comms.portName.get());
			rb.setSelected(false);
			break;
		case ItemEvent.DESELECTED:
			Comms.theDriver.close();
			break;
		}
		
	}

	
}
