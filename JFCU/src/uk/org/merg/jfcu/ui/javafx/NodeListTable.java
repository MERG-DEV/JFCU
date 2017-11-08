package uk.org.merg.jfcu.ui.javafx;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Module;

public class NodeListTable extends TableView<Module> {
	private TableColumn<Module, Integer> canidCol;
	
	@SuppressWarnings("unchecked")
	public NodeListTable() {
		TableColumn<Module,String> nodeTypeCol = new TableColumn<Module,String>("Node Type");
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<Module,String>("moduleTypeName"));
        
        TableColumn<Module,String> nodeNameCol = new TableColumn<Module,String>("Node Name");
        nodeNameCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Integer> nodeNoCol = new TableColumn<Module,Integer>("Node No");
        nodeNoCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("nodeNumber"));
        
        canidCol = new TableColumn<Module,Integer>("CAN ID");
        canidCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("canid"));
        
        TableColumn<Module, Integer> eventsCol = new TableColumn<Module,Integer>("Events");
        //eventsCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Boolean> fLiMCol = new TableColumn<Module,Boolean>("FLiM");
        //fLiMCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Integer> maxEventsCol = new TableColumn<Module,Integer>("Max Events");
        //maxEventsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("name"));
        
        TableColumn<Module,String> versionCol = new TableColumn<Module,String>("Version");
        versionCol.setCellValueFactory(new PropertyValueFactory<Module, String>("fullVersion"));
        
        TableColumn<Module, Integer> noNvsCol = new TableColumn<Module,Integer>("No. NVs");
        noNvsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("numNV"));
        
        TableColumn<Module,String> procIdCol = new TableColumn<Module,String>("Proc Id");
        //procIdCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module,String> nodeVarsCol = new TableColumn<Module,String>("Node Vars");
        //nodeVarsCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        

        setItems(Globals.layout.getModulesProperty());
        
        setTableMenuButtonVisible(true);
        
        getColumns().setAll(nodeTypeCol, nodeNameCol, nodeNoCol, canidCol, eventsCol, 
        		fLiMCol, maxEventsCol, versionCol, noNvsCol, procIdCol, nodeVarsCol);
        
        // The Node popup
        setRowFactory(new NodeListCallback());
        
	}

}
