package uk.org.merg.jfcu.ui.javafx;


import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Callback;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.controller.ModuleTypeDataCache;
import uk.org.merg.jfcu.events.ByteOp;
import uk.org.merg.jfcu.layoutmodel.Event;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleType;

public class NodeListTable extends TableView<Module> {
	
	@SuppressWarnings("unchecked")
	public NodeListTable() {
		TableColumn<Module,String> nodeTypeCol = new TableColumn<Module,String>("Node Type");
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<Module,String>("moduleTypeName"));
        
        TableColumn<Module,String> nodeNameCol = new TableColumn<Module,String>("Node Name");
        nodeNameCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        nodeNameCol.setEditable(true);
        nodeNameCol.setCellFactory(TextFieldTableCell.<Module>forTableColumn());
        nodeNameCol.setOnEditCommit(
            (CellEditEvent<Module, String> t) -> {
                ((Module) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
        });
        setEditable(true);
        
        TableColumn<Module, Integer> nodeNoCol = new TableColumn<Module,Integer>("Node No");
        nodeNoCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("nodeNumber"));
        
        TableColumn<Module,Integer> canidCol = new TableColumn<Module,Integer>("CAN ID");
        canidCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("canid"));
        
        TableColumn<Module, Integer> produceCol = new TableColumn<Module,Integer>("Produce");
        produceCol.setCellFactory(new Callback<TableColumn<Module, Integer>, TableCell<Module, Integer>>(){
			@Override
			public TableCell<Module, Integer> call(TableColumn<Module, Integer> arg0) {
				return new ProducerEventCell();
			}});
        
        TableColumn<Module, Integer> consumeCol = new TableColumn<Module,Integer>("Consume");
        consumeCol.setCellFactory(new Callback<TableColumn<Module, Integer>, TableCell<Module, Integer>>(){
			@Override
			public TableCell<Module, Integer> call(TableColumn<Module, Integer> arg0) {
				return new ConsumerEventCell();
			}});
        
        TableColumn<Module, Boolean> fLiMCol = new TableColumn<Module,Boolean>("FLiM");
        fLiMCol.setCellValueFactory(new PropertyValueFactory<Module, Boolean>("flimFlag"));
        
        TableColumn<Module, Integer> maxEventsCol = new TableColumn<Module,Integer>("Max Events");
        maxEventsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("numEvents"));
        
        TableColumn<Module,String> versionCol = new TableColumn<Module,String>("Version");
        versionCol.setCellValueFactory(new PropertyValueFactory<Module, String>("fullVersion"));
        
        TableColumn<Module, Integer> noNvsCol = new TableColumn<Module,Integer>("No. NVs");
        noNvsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("numNv"));
        
        TableColumn<Module,String> procIdCol = new TableColumn<Module,String>("Proc Id");
        procIdCol.setCellValueFactory(new PropertyValueFactory<Module, String>("processor"));
        
        TableColumn<Module,String> nodeVarsCol = new TableColumn<Module,String>("Node Vars");
        //nodeVarsCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        

        setItems(Globals.layout.getModulesProperty());
        
        setTableMenuButtonVisible(true);
        
        getColumns().setAll(nodeTypeCol, nodeNameCol, nodeNoCol, canidCol, produceCol, consumeCol, 
        		fLiMCol, maxEventsCol, versionCol, noNvsCol, procIdCol, nodeVarsCol);
        
        // The Node popup
        setRowFactory(new NodeListCallback());
        
        

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.getDragboard();
                boolean success = false;
                if (event.getDragboard().hasString()) {            

                    //String text = db.getString();
                    //tableContent.add(text);
                    //tableView.setItems(tableContent);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            } 
        });   
        
	}
	
	
	private class ProducerEventCell extends TableCell<Module, Integer> {
		public ProducerEventCell() {
			setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent dragEvent) {
					// data is dragged over the target 
					Dragboard db = dragEvent.getDragboard();
					if (db.hasString()){
						dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					}
					dragEvent.consume();
				}
			});
			setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent dragEvent) {
					// data is dragged over the target
					System.out.println("Dropped onto producer "+dragEvent.getDragboard().getString());
					Dragboard db = dragEvent.getDragboard();
					if (db.hasString()){
						Event e = Event.fromSerialised(db.getString());
						System.out.println("Got e="+e);
						dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
						
						
						final Module m = (Module) getTableRow().itemProperty().getValue();
						System.out.println("Producer Popup on Module "+m);
						if (m == null) return;
							    
						final Popup popup = new Popup();
						popup.setAutoHide(true);
						popup.setAutoFix(true);
								
						ModuleType mt = ModuleTypeDataCache.get(m.getModuleTypeName(), 
								m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).intValue(), 
								(char) m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).intValue());
								
						EditEvPopup popupBox = new EditEvPopup(popup, m, mt.getProducedEvents());
						popup.getContent().addAll(popupBox);
						popup.show(JFCUfx.stage);
						Map<Integer, ByteOp> evops = popupBox.getEvOps();
						// now merge the ops
						
						
					}
					dragEvent.consume();
				}
			});
		}
	}
	
	private class ConsumerEventCell extends TableCell<Module, Integer> {
		public ConsumerEventCell() {
			setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent dragEvent) {
					dragEvent.getDragboard();
					if (dragEvent.getDragboard().hasString()){
						dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					}
					dragEvent.consume();
				}
			});
			setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent dragEvent) {
					// data is dragged over the target
					System.out.println("Dropped onto comsumer "+dragEvent.getDragboard().getString());
					Dragboard db = dragEvent.getDragboard();
					if (db.hasString()){
						Event e = Event.fromSerialised(db.getString());
						System.out.println("Got e="+e);
						dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
						
						  
					   
						final Module m = (Module) getTableRow().itemProperty().getValue();
						System.out.println("Consumer Popup on Module "+m);
						if (m == null) return;
							    
						final Popup popup = new Popup();
						popup.setAutoHide(true);
						popup.setAutoFix(true);
								
						ModuleType mt = ModuleTypeDataCache.get(m.getModuleTypeName(), 
								m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).intValue(), 
								(char) m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).intValue());
								
						VBox popupBox = new EditEvPopup(popup, m, mt.getConsumedEvents());
						popup.getContent().addAll(popupBox);
						popup.show(JFCUfx.stage);
	
					}
					dragEvent.consume();
				}
			});
		}
	}

}
