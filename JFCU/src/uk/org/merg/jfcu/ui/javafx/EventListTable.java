package uk.org.merg.jfcu.ui.javafx;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Event;

public class EventListTable extends TableView<Event> {
	@SuppressWarnings("unchecked")
	public EventListTable() {
        TableColumn<Event,String> nameCol = new TableColumn<Event,String>("Event Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        nameCol.setEditable(true);
        nameCol.setCellFactory(TextFieldTableCell.<Event>forTableColumn());
        nameCol.setOnEditCommit(
            (CellEditEvent<Event, String> t) -> {
                ((Event) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
        });
        
        TableColumn<Event,Event.Etype> lengthCol = new TableColumn<Event,Event.Etype>("Length");
        lengthCol.setCellValueFactory(new PropertyValueFactory<Event,Event.Etype>("length"));
        
        TableColumn<Event,Integer> nnCol = new TableColumn<Event,Integer>("Node Number");
        nnCol.setCellValueFactory(new PropertyValueFactory<Event,Integer>("nn"));
        
        TableColumn<Event,Integer> enCol = new TableColumn<Event,Integer>("Event/Device Number");
        enCol.setCellValueFactory(new PropertyValueFactory<Event,Integer>("en"));
        
//        TableColumn<Event,String> eventVarCol = new TableColumn<Event,String>("Evs");       
//        TableColumn<Event,Integer> eventNumberCol = new TableColumn<Event,Integer>("Event/Device Number");
 
//        ObservableList<Event> events = new SimpleListProperty<Event>(FXCollections.observableArrayList());
//        modules.addAll(uk.org.merg.jfcu.ui.swing.JFCU.layout.getModules());
        setEditable(true);
        
        setItems(Globals.layout.eventsProperty());
        
        setTableMenuButtonVisible(true);
        
        getColumns().setAll(nameCol, lengthCol, nnCol, enCol);
        
        setOnDragDetected(new EventHandler<MouseEvent>() { //drag
            @Override
            public void handle(MouseEvent event) {
                // drag was detected, start drag-and-drop gesture
                String selected = getSelectionModel().getSelectedItem().toSerialised();
                if(selected !=null){
                	System.out.println("Drag of Event started = "+selected);
                    Dragboard db = startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(selected);
                    db.setContent(content);
                    event.consume(); 
                }
            }
        });
	}
}
