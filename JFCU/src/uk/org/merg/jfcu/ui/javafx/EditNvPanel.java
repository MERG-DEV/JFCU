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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import uk.org.merg.jfcu.controller.ModuleTypeDataCache;
import uk.org.merg.jfcu.layoutmodel.Module;
import uk.org.merg.jfcu.modulemodel.ModuleType;
import uk.org.merg.jfcu.modulemodel.NvBits;
import uk.org.merg.jfcu.modulemodel.NvByte;
import uk.org.merg.jfcu.modulemodel.NvGroup;
import uk.org.merg.jfcu.modulemodel.NvItem;

public class EditNvPanel extends VBox {
	public EditNvPanel(Popup parentPopup, Module m) {
		getStyleClass().add("popup");
		VBox scrollBox = new VBox();
		// find the ModuleType
		String typeName = m.getModuleTypeName()+"v"+m.getVersion();
		ModuleType mt = ModuleTypeDataCache.get(typeName);
		if (mt == null) {
			Alert alert = new Alert(AlertType.ERROR, "Modules of type \""+typeName+"\" are not currently supported.");
			alert.showAndWait();
		} else {
			for (NvGroup group : mt.getNvGroups()) {
				VBox groupBox = new VBox();
				groupBox.getStyleClass().add("nvgroup");
				Label l;
				
				l = new Label(group.getName());
				l.getStyleClass().add("nvGroupTitle");
				groupBox.getChildren().add(l);
			
				GridPane nvGrid = new GridPane();
				// do the titles
				l = new Label("NV #");
				l.getStyleClass().add("nvHeader");
				nvGrid.add(l, 0, 0);
			
				l = new Label("Name");
				l.getStyleClass().add("nvHeader");
				nvGrid.add(l, 1, 0);
				
				l = new Label("Value");
				l.getStyleClass().add("nvHeader");
				nvGrid.add(l, 2, 0);
			
				l = new Label("Description");
				l.getStyleClass().add("nvHeader");
				nvGrid.add(l, 3, 0);
			
				int idx = 1;
				for (NvByte nvByte: group.getNvs()) {
					for (NvBits nvBits : nvByte.getNvbits()) {
						// the id
						l = new Label(""+nvByte.getId());
						l.getStyleClass().add("nvGroupCell");
						nvGrid.add(l, 0,  idx);
						// the name
						l = new Label(nvBits.getName());
						l.getStyleClass().add("nvGroupCell");
						nvGrid.add(l, 1,  idx);
						// the value
						Byte v = m.getNvs().get(nvByte.getId());
						if (v == null) v = 0;
						if ((nvBits.getNvType().getUi() == null) || "text".equals(nvBits.getNvType().getUi())) {
							TextField valueCell = new TextField(""+(v&nvBits.getBitmask()));
							valueCell.getStyleClass().add("nvGroupCell");
							valueCell.setMaxWidth(40);
							nvGrid.add(valueCell, 2,  idx);
						} else if ("choice".equals(nvBits.getNvType().getUi())) {
							ComboBox<String> combo = new ComboBox<String>();
							for (NvItem i : nvBits.getNvType().getNvChoice().getNvitems()) {
								combo.getItems().add(i.getSetting());
								if (v == i.getValue()) {
									combo.getSelectionModel().select(i.getSetting());
								}
							}
							combo.getStyleClass().add("nvGroupCell");
							nvGrid.add(combo, 2,  idx);
						} else if ("checkbox".equals(nvBits.getNvType().getUi())) {
							CheckBox valueCell = new CheckBox();
							valueCell.setSelected((v&nvBits.getBitmask()) != 0);
							valueCell.getStyleClass().add("nvGroupCell");
							nvGrid.add(valueCell, 2,  idx);
						}
					
						// the description
						l = new Label(nvBits.getDescription());
						l.getStyleClass().add("nvGroupCell");
						nvGrid.add(l, 3,  idx);
					
						idx++;
					}
				}
				groupBox.getChildren().add(nvGrid);
				scrollBox.getChildren().add(groupBox);
			}
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(scrollBox);
			scrollPane.setPrefHeight(600);
		
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
