package uk.org.merg.jfcu.ui.javafx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.controller.ModuleTypeDataCache;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleType;
import uk.org.merg.jfcu.modulemodel.Bits;
import uk.org.merg.jfcu.modulemodel.EvTab;
import uk.org.merg.jfcu.modulemodel.MyByte;
import uk.org.merg.jfcu.modulemodel.Group;
import uk.org.merg.jfcu.modulemodel.Item;
import uk.org.merg.jfcu.events.ByteOp;

public class EditEvPopup extends VBox {
	private Map<Integer, ByteOp> evOps;
	
	public EditEvPopup(Popup parentPopup, Module m, List<EvTab> tabs) {
		evOps = new HashMap<Integer, ByteOp>();
		
		getStyleClass().add("popup");
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		
		// find the ModuleType
		String typeName = m.getModuleTypeName();
		if (typeName == null) {
			Alert alert = new Alert(AlertType.ERROR, "Modules of type \""+typeName+"\" are not currently supported.");
			alert.showAndWait();
			return;
		}
		System.out.println("m="+m);
		ModuleType moduleType = ModuleTypeDataCache.get(m.getModuleTypeName(), 
				m.getParams().get(CbusProperties.MAJOR_VERSION.getValue()).intValue(), 
				(char) m.getParams().get(CbusProperties.MINOR_VERSION.getValue()).intValue());
		if (moduleType == null) {
			Alert alert = new Alert(AlertType.ERROR, "Modules of type \""+typeName+"\" are not currently supported.");
			alert.showAndWait();
		} else {
			for (EvTab evTab : tabs) {
				Tab tab = new Tab();
				tab.setText(evTab.getName());
				tabPane.getTabs().add(tab);
				VBox scrollBox = new VBox();
				for (Group group : evTab.getGroups()) {
					VBox groupBox = new VBox();
					groupBox.getStyleClass().add("evgroup");
					Label l;
					StackPane sp;
				
					l = new Label(group.getName());
					l.getStyleClass().add("evGroupTitle");
					groupBox.getChildren().add(l);
			
					GridPane evGrid = new GridPane();
					evGrid.getStyleClass().add("evGrid");
					// do the titles
					l = new Label("EV #");
					sp = new StackPane();
					sp.getStyleClass().add("evHeader");
					sp.getChildren().add(l);
					evGrid.add(sp, 0, 0);
			
					l = new Label("Name");
					sp = new StackPane();
					sp.getStyleClass().add("evHeader");
					sp.getChildren().add(l);
					evGrid.add(sp, 1, 0);
				
					l = new Label("Value");
					sp = new StackPane();
					sp.getStyleClass().add("evHeader");
					sp.getChildren().add(l);
					evGrid.add(sp, 2, 0);
			
					l = new Label("Description");
					sp = new StackPane();
					sp.getStyleClass().add("evHeader");
					sp.getChildren().add(l);
					evGrid.add(sp, 3, 0);
			
					int idx = 1;
					for (MyByte evByte: group.getBytes()) {
						for (Bits evBits : evByte.getBits()) {
							// the id
							l = new Label(""+evByte.getId());
							sp = new StackPane();
							sp.getStyleClass().add("evGroupCell");
							sp.getChildren().add(l);
							evGrid.add(sp, 0,  idx);
							// the name
							l = new Label(evBits.getName());
							sp = new StackPane();
							sp.getStyleClass().add("evGroupCell");
							sp.getChildren().add(l);
							evGrid.add(sp, 1,  idx);

							Byte v = 0;
							
							if ((evBits.getType().getUi() == null) || "text".equals(evBits.getType().getUi())) {
								TextField valueCell = new TextField(""+(v&evBits.getBitmask()));
								sp = new StackPane();
								sp.getStyleClass().add("evGroupCell");
								sp.getChildren().add(valueCell);
								valueCell.setMaxWidth(40);
								evGrid.add(sp, 2,  idx);
							} else if ("choice".equals(evBits.getType().getUi())) {
								ComboBox<String> combo = new ComboBox<String>();
								for (Item i : evBits.getType().getChoice().getItems()) {
									combo.getItems().add(i.getSetting());
									if (v == i.getValue()) {
										combo.getSelectionModel().select(i.getSetting());
									}
								}
								sp = new StackPane();
								sp.getStyleClass().add("evGroupCell");
								sp.getChildren().add(combo);
								evGrid.add(sp, 2,  idx);
							} else if ("checkbox".equals(evBits.getType().getUi())) {
								CheckBox valueCell = new CheckBox();
								valueCell.setSelected((v&evBits.getBitmask()) != 0);
								sp = new StackPane();
								sp.getStyleClass().add("evGroupCell");
								sp.getChildren().add(valueCell);
								evGrid.add(sp, 2,  idx);
							}
					
							// the description
							l = new Label(evBits.getDescription());
							sp = new StackPane();
							sp.getStyleClass().add("evGroupCell");
							sp.getChildren().add(l);
							evGrid.add(sp, 3,  idx);
					
							idx++;
						}
					}
					groupBox.getChildren().add(evGrid);
					scrollBox.getChildren().add(groupBox);
					tab.setContent(scrollBox);
				}
				
			}
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(tabPane);
			//scrollPane.setPrefHeight(600);
		
			ButtonBar buttons = new ButtonBar();
			Button b = new Button("Write");
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					parentPopup.hide();
				}});
			ButtonBar.setButtonData(b, ButtonData.YES);
			buttons.getButtons().add(b);
			b = new Button("Cancel");
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					parentPopup.hide();
				}});
			ButtonBar.setButtonData(b, ButtonData.NO);
			buttons.getButtons().add(b);
		
			getChildren().addAll(scrollPane, buttons);
		}
	}
	
	public Map<Integer, ByteOp> getEvOps() {
		return evOps;
	}
}
