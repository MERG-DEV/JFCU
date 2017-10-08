package uk.org.merg.jfcu.ui.javafx;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uk.org.merg.jfcu.layoutmodel.Event;

public class EventListTable extends TableView<Event> {
	@SuppressWarnings("unchecked")
	public EventListTable() {
        TableColumn<Event,String> eventRefCol = new TableColumn<Event,String>("Event Ref");
        //firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Event,String> producerNodeCol = new TableColumn<Event,String>("Producer Node");
        //lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Event,String> eventNameCol = new TableColumn<Event,String>("Event/Device Name");
        TableColumn<Event,String> eventVarCol = new TableColumn<Event,String>("Event Variables");
        TableColumn<Event,Integer> eventNodeCol = new TableColumn<Event,Integer>("Event Node Number");
        TableColumn<Event,Integer> eventNumberCol = new TableColumn<Event,Integer>("Event/Device Number");
 
        ObservableList<Event> events = new SimpleListProperty<Event>(FXCollections.observableArrayList());
//        modules.addAll(uk.org.merg.jfcu.ui.swing.JFCU.layout.getModules());
        setItems(events);
        getColumns().setAll(eventRefCol, producerNodeCol, eventNameCol, eventVarCol, 
        		eventNodeCol, eventNumberCol);
	}
}
