package uk.org.merg.jfcu.ui.javafx;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Event;

public class EventListTable extends TableView<Event> {
	@SuppressWarnings("unchecked")
	public EventListTable() {
        TableColumn<Event,String> nameCol = new TableColumn<Event,String>("Event Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
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

        
        setItems(Globals.layout.eventsProperty());
        
        setTableMenuButtonVisible(true);
        
        getColumns().setAll(nameCol, lengthCol, nnCol, enCol);
	}
}
