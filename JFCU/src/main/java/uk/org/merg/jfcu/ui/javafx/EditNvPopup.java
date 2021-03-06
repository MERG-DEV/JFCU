package uk.org.merg.jfcu.ui.javafx;

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
import uk.org.merg.jfcu.modulemodel.MyByte;
import uk.org.merg.jfcu.modulemodel.Group;
import uk.org.merg.jfcu.modulemodel.Item;
import uk.org.merg.jfcu.modulemodel.NvTab;

public class EditNvPopup extends VBox {
	public EditNvPopup(Popup parentPopup, Module m) {
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
			for (NvTab nvTab : moduleType.getNvTabs()) {
				Tab tab = new Tab();
				tab.setText(nvTab.getName());
				tabPane.getTabs().add(tab);
				VBox scrollBox = new VBox();
				for (Group group : nvTab.getGroups()) {
					VBox groupBox = new VBox();
					groupBox.getStyleClass().add("nvgroup");
					Label l;
					StackPane sp;
				
					l = new Label(group.getName());
					l.getStyleClass().add("nvGroupTitle");
					groupBox.getChildren().add(l);
			
					GridPane nvGrid = new GridPane();
					nvGrid.getStyleClass().add("nvGrid");
					// do the titles
					l = new Label("NV #");
					sp = new StackPane();
					sp.getStyleClass().add("nvHeader");
					sp.getChildren().add(l);
					nvGrid.add(sp, 0, 0);
			
					l = new Label("Name");
					sp = new StackPane();
					sp.getStyleClass().add("nvHeader");
					sp.getChildren().add(l);
					nvGrid.add(sp, 1, 0);
				
					l = new Label("Value");
					sp = new StackPane();
					sp.getStyleClass().add("nvHeader");
					sp.getChildren().add(l);
					nvGrid.add(sp, 2, 0);
			
					l = new Label("Description");
					sp = new StackPane();
					sp.getStyleClass().add("nvHeader");
					sp.getChildren().add(l);
					nvGrid.add(sp, 3, 0);
			
					int idx = 1;
					for (MyByte nvByte: group.getBytes()) {
						for (Bits nvBits : nvByte.getBits()) {
							// the id
							l = new Label(""+nvByte.getId());
							sp = new StackPane();
							sp.getStyleClass().add("nvGroupCell");
							sp.getChildren().add(l);
							nvGrid.add(sp, 0,  idx);
							// the name
							l = new Label(nvBits.getName());
							sp = new StackPane();
							sp.getStyleClass().add("nvGroupCell");
							sp.getChildren().add(l);
							nvGrid.add(sp, 1,  idx);
							// the value
							Integer nvVal = m.getNvs().get(nvByte.getId());
							Byte v = 0;
							if (nvVal != null) {
								v = nvVal.byteValue();
							}
							if ((nvBits.getType().getUi() == null) || "text".equals(nvBits.getType().getUi())) {
								TextField valueCell = new TextField(""+(v&nvBits.getBitmask()));
								sp = new StackPane();
								sp.getStyleClass().add("nvGroupCell");
								sp.getChildren().add(valueCell);
								valueCell.setMaxWidth(40);
								nvGrid.add(sp, 2,  idx);
							} else if ("choice".equals(nvBits.getType().getUi())) {
								ComboBox<String> combo = new ComboBox<String>();
								for (Item i : nvBits.getType().getChoice().getItems()) {
									combo.getItems().add(i.getSetting());
									if (v == i.getValue()) {
										combo.getSelectionModel().select(i.getSetting());
									}
								}
								sp = new StackPane();
								sp.getStyleClass().add("nvGroupCell");
								sp.getChildren().add(combo);
								nvGrid.add(sp, 2,  idx);
							} else if ("checkbox".equals(nvBits.getType().getUi())) {
								CheckBox valueCell = new CheckBox();
								valueCell.setSelected((v&nvBits.getBitmask()) != 0);
								sp = new StackPane();
								sp.getStyleClass().add("nvGroupCell");
								sp.getChildren().add(valueCell);
								nvGrid.add(sp, 2,  idx);
							}
					
							// the description
							l = new Label(nvBits.getDescription());
							sp = new StackPane();
							sp.getStyleClass().add("nvGroupCell");
							sp.getChildren().add(l);
							nvGrid.add(sp, 3,  idx);
					
							idx++;
						}
					}
					groupBox.getChildren().add(nvGrid);
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
}
