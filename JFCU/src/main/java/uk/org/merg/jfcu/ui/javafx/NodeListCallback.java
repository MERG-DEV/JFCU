package uk.org.merg.jfcu.ui.javafx;

import co.uk.ccmr.cbus.sniffer.CbusEvent;
import co.uk.ccmr.cbus.sniffer.Opc;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MinPri;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MjPri;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Callback;
import uk.org.merg.jfcu.cbus.Comms;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.layoutmodel.Module;


public class NodeListCallback implements Callback<TableView<Module>, TableRow<Module>> {
	public TableRow<Module> call(TableView<Module> tableView) {
	    final TableRow<Module> row = new TableRow<Module>();
	    final ContextMenu rowMenu = new ContextMenu();

	    
	    
	    MenuItem readProps = new MenuItem("Read Properties");
	    readProps.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Start to read parameters
				final Module m = row.itemProperty().getValue();
			    System.out.println("Popup on Module "+m);
			    if (m == null) return;
				
			    CbusEvent msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.RQNPN);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, CbusProperties.MODULEID.getValue());		// param 3 is module id
				Comms.theDriver.queueForTransmit(msg);
				
			}});
	    MenuItem readNvs = new MenuItem("Read NVs");
	    readNvs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			    final Module m = row.itemProperty().getValue();
			    System.out.println("Popup on Module "+m);
			    if (m == null) return;
			    
				// start to read the NVs at NV#1
				CbusEvent msg = new CbusEvent();
				msg.setMinPri(MinPri.HIGH);
				msg.setMjPri(MjPri.HIGH);
				msg.setCANID(Globals.CANID);
				msg.setOpc(Opc.NVRD);
				msg.setNN(m.getNodeNumber());
				msg.setData(2, 1);	
				Comms.theDriver.queueForTransmit(msg);
			}});
	    MenuItem editNvs = new MenuItem("Edit/update NVs");
	    editNvs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			    final Module m = row.itemProperty().getValue();
			    System.out.println("NV Popup on Module "+m);
			    if (m == null) return;
			    
				final Popup popup = new Popup();
				popup.setAutoHide(true);
				popup.setAutoFix(true);
				
				VBox popupBox = new EditNvPopup(popup, m);
				popup.getContent().addAll(popupBox);
				popup.show(JFCUfx.stage);
			}});
	    MenuItem writeNvs = new MenuItem("Write NVs");
	    writeNvs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem readEvents = new MenuItem("Read events");
	    readEvents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem writeEvents = new MenuItem("Write events");
	    writeEvents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem deleteEvents = new MenuItem("Delete all events");
	    deleteEvents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem enumerate = new MenuItem("CANID enumeration");
	    enumerate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem canid = new MenuItem("Set CANID");
	    canid.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    MenuItem program = new MenuItem("Program Module");
	    program.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    
	    
	    
	  
	    
	    
	    
	    rowMenu.getItems().addAll(readProps, readNvs, editNvs, writeNvs, readEvents, writeEvents,deleteEvents, enumerate, canid, program);

	    // only display context menu for non-null items:
	    row.contextMenuProperty().bind(
	      Bindings.when(Bindings.isNotNull(row.itemProperty()))
	      .then(rowMenu)
	      .otherwise((ContextMenu)null));
	    return row;
	  }


}
