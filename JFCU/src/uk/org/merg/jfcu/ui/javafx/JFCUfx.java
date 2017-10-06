package uk.org.merg.jfcu.ui.javafx;

import java.io.File;
import java.util.Optional;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import co.uk.cbusio.ui.LimitLinesDocumentListener;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import uk.org.merg.jfcu.cbus.Comms;
import uk.org.merg.jfcu.cbus.Dummy;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.layoutmodel.Event;
import uk.org.merg.jfcu.layoutmodel.Layout;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleDefs;

public class JFCUfx extends Application {
	private String portText;
	public static Stage stage;
	@SuppressWarnings("unchecked")
	@Override
    public void start(Stage stage) {
		JFCUfx.stage = stage;
		stage.setTitle("JavaFX FLiM Configuration Utility");
		
		Scene scene = new Scene(new VBox(), 800.0d, 400.0d);
        //scene.setFill(Color.web("#808080"));
        scene.getStylesheets().clear();
        File f = new File("jfcu.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        
        MenuItem mi = new MenuItem("Open...");
        mi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				 FileChooser fileChooser = new FileChooser();
				 fileChooser.setTitle("Open Layout File");
				 fileChooser.getExtensionFilters().addAll(
				         new ExtensionFilter("XML Files", "*.xml"),
				         new ExtensionFilter("All Files", "*.*"));
				 File selectedFile = fileChooser.showOpenDialog(stage);
				 if (selectedFile != null) {
				    Globals.layout = Layout.load(selectedFile.getAbsolutePath());
				 }
			}});
        menuFile.getItems().add(mi);
        
        mi = new MenuItem("Save...");
        mi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				 fileChooser.setTitle("Save Layout File");
				 fileChooser.getExtensionFilters().addAll(
				         new ExtensionFilter("XML Files", "*.xml"),
				         new ExtensionFilter("All Files", "*.*"));
				 File selectedFile = fileChooser.showOpenDialog(stage);
				 if (selectedFile != null) {
				    Globals.layout.save(selectedFile.getAbsolutePath());
				 }
			}});
        menuFile.getItems().add(mi);
        
        mi = new MenuItem("Load from CBUS");
        mi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Globals.layout.getModules().clear();
				Globals.layout.getModulesProperty().clear();
				Globals.layout.loadFromCBUS();
			}});
        menuFile.getItems().add(mi);
        
        mi = new MenuItem("Exit");
        mi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}});
        menuFile.getItems().add(mi);
        
        Menu menuEvents = new Menu("Events");
        Menu menuNodes = new Menu("Nodes");
        Menu menuCommunications = new Menu("Communications");
        
        ToggleGroup connectGroup = new ToggleGroup();
        String[] portList;
        portList = Comms.theDriver.getPortNames();
        if (portList.length > 0) portText=null;
        System.out.println("Portlist len="+portList.length);
        for (String p : portList) {
        	RadioMenuItem item = new RadioMenuItem(p);
        	item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        System.out.println("radio 1 toggled e="+e);
			        Comms.connect(item.getText());
			    }
			});
        	connectGroup.getToggles().add(item);
        	menuCommunications.getItems().add(item);
        }
        if ((portText != null) && (portText.length() > 0)) {
        	RadioMenuItem item = new RadioMenuItem(portText);
        	item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        System.out.println("radio 2 toggled e="+e);
			        Comms.connect(item.getText());
			    }
			});
        	connectGroup.getToggles().add(item);
        	menuCommunications.getItems().add(item);
        }
		  mi = new MenuItem("Define Port");
	      mi.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Define Port");
					dialog.setHeaderText("Enter port device name");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						portText = result.get();
					    RadioMenuItem item = new RadioMenuItem(portText);
	        			item.setOnAction(new EventHandler<ActionEvent>() {
	        			    @Override public void handle(ActionEvent e) {
	        			        System.out.println("radio 3 toggled e="+e);
	        			        Comms.connect(item.getText());
	        			    }
	        			});
	                	connectGroup.getToggles().add(item);
	                	menuCommunications.getItems().add(item);
	                }
	        	}
	      });
	      menuCommunications.getItems().add(mi);
        
        
        
        Menu menuSettings = new Menu("Settings");
        Menu menuLogging = new Menu("Logging");
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().addAll(menuFile, menuEvents, menuNodes, menuCommunications, menuSettings,
        		menuLogging, menuHelp);
 
        HBox hbox = new HBox();
        
        // Node list
        TableView<Module> nodeList = new TableView<Module>();
        TableColumn<Module,String> nodeTypeCol = new TableColumn<Module,String>("Node Type");
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<Module,String>("moduleTypeName"));
        
        TableColumn<Module,String> nodeNameCol = new TableColumn<Module,String>("Node Name");
        nodeNameCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Integer> nodeNoCol = new TableColumn<Module,Integer>("Node No");
        nodeNoCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("nodeNumber"));
        
        TableColumn<Module, Integer> eventsCol = new TableColumn<Module,Integer>("Events");
        //eventsCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Boolean> fLiMCol = new TableColumn<Module,Boolean>("FLiM");
        //fLiMCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module, Integer> maxEventsCol = new TableColumn<Module,Integer>("Max Events");
        //maxEventsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("name"));
        
        TableColumn<Module,String> versionCol = new TableColumn<Module,String>("Version");
        versionCol.setCellValueFactory(new PropertyValueFactory<Module, String>("version"));
        
        TableColumn<Module, Integer> noNvsCol = new TableColumn<Module,Integer>("No. NVs");
        noNvsCol.setCellValueFactory(new PropertyValueFactory<Module, Integer>("numNV"));
        
        TableColumn<Module,String> procIdCol = new TableColumn<Module,String>("Proc Id");
        //procIdCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        
        TableColumn<Module,String> nodeVarsCol = new TableColumn<Module,String>("Node Vars");
        //nodeVarsCol.setCellValueFactory(new PropertyValueFactory<Module, String>("name"));
        

        nodeList.setItems(Globals.layout.getModulesProperty());
        
        nodeList.getColumns().setAll(nodeTypeCol, nodeNameCol, nodeNoCol, eventsCol, fLiMCol, maxEventsCol,
        		versionCol, noNvsCol, procIdCol, nodeVarsCol);
        nodeList.setRowFactory(new NodeListCallback());
        
        
        // Event list
        TableView<Event> eventList = new TableView<Event>();
        TableColumn<Event,String> eventRefCol = new TableColumn<Event,String>("Event Ref");
        //firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Event,String> producerNodeCol = new TableColumn<Event,String>("Producer Node");
        //lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Event, String> eventNameCol = new TableColumn<Event,String>("Event/Device Name");
        TableColumn<Event, String> eventVarCol = new TableColumn<Event,String>("Event Variables");
        TableColumn<Event, Integer> eventNodeCol = new TableColumn<Event,Integer>("Event Node Number");
        TableColumn<Event, Integer> eventNumberCol = new TableColumn<Event,Integer>("Event/Device Number");
 
        ObservableList<Event> events = new SimpleListProperty<Event>(FXCollections.observableArrayList());
//        modules.addAll(uk.org.merg.jfcu.ui.swing.JFCU.layout.getModules());
        eventList.setItems(events);
        eventList.getColumns().setAll(eventRefCol, producerNodeCol, eventNameCol, eventVarCol, 
        		eventNodeCol, eventNumberCol);
        //Log
        TextArea log = new TextArea("> QNN\n< PNN NN=1111, Manu=MERG, Module=CANMIO, Flags=FLiM&BOOT\n\n\n\n\n\n\n\n\n\n");
        Thread logUpdater = new Thread() {
        	@Override
			public void run(){
        		while (true) {
        			try {
						log.setText(Globals.log.getText(0, Globals.log.getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
        			try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
        	}
        };
        logUpdater.start();
        log.getStyleClass().add("cbus-log");
        log.setPrefRowCount(1000);
        //Region region = ( Region ) log.lookup( ".content" );
        //System.out.println("region="+region);
        //region.setStyle( "-fx-background-color: yellow" );
        VBox lhs = new VBox();
        Label lhsTitle = new Label("User CBUS Nodes");
        lhsTitle.getStyleClass().add("lhs-title");
        lhs.getChildren().addAll(lhsTitle, nodeList);
        lhsTitle = new Label("Events associated with Node");
        lhsTitle.getStyleClass().add("lhs-title");
        lhs.getChildren().addAll(lhsTitle, eventList);
        
        VBox rhs = new VBox();
        HBox rhsTitle = new HBox();
        Label rhsLabel = new Label("CBUS log");
        rhsLabel.getStyleClass().add("rhs-title");
        rhsTitle.getChildren().add(rhsLabel);
        Button button = new Button("Clear");
        
        button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Globals.log.remove(0,Globals.log.getLength());
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
        rhsTitle.getChildren().add(button);
        
/*        // test code
        button = new Button("Test");
        button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Module m = Globals.layout.findModule(1111);
				if (m == null) {
					System.out.println("not found");
					return;
				}
				int nnv = m.getNumNV();
				System.out.println("got nnv="+nnv);
				m.setNumNV(++nnv);
				System.out.println("put nnv="+nnv);
			}});
        rhsTitle.getChildren().add(button);
        //end text code/* */
        
        
        rhs.getChildren().addAll(rhsTitle, log);
        hbox.getChildren().addAll(lhs, rhs);  
        
        HBox bottomBox = new HBox();
        String driverName = Comms.theDriver.getClass().toString();
        String [] driverNameParts = driverName.split("\\.");
        Label bottomLabel = new Label("Driver:"+driverNameParts[driverNameParts.length-1]);
        bottomLabel.getStyleClass().add("bottomLabel");
        bottomBox.getChildren().add(bottomLabel);
        HBox portLabelBox = new HBox();
        portLabelBox.getStyleClass().add("bottomLabel");
        bottomLabel = new Label("COM Port:");
        portLabelBox.getChildren().add(bottomLabel);
        bottomLabel = new Label(Comms.portName.get());
        bottomLabel.textProperty().bind(Comms.portName);
        portLabelBox.getChildren().add(bottomLabel);
        bottomBox.getChildren().add(portLabelBox);
        bottomBox.setId("bottomBox");
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, hbox, bottomBox);
 
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {
    	Globals.ui = Globals.UiChoice.JAVAFX;
    	JTextPane textPane = new JTextPane();
    	Globals.init(textPane.getStyledDocument());
    	Globals.log.addDocumentListener(new LimitLinesDocumentListener(Globals.MAX_LINES));
    	
    	Dummy.buildDummyLayout();
    	Comms.prepareCommsDriver(args);
		ModuleDefs.loadModuleDefinitions();
        launch(args);
    }

}