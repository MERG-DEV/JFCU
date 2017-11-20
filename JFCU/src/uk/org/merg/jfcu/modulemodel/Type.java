package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import uk.org.merg.jfcu.layoutmodel.Module;

@XmlRootElement
public class Type {
	private String ui;
	@XmlElementRef(name="choice", required=false)
	private Choice choice;
	@XmlElementRef(name="integer", required=false)
	private MyInteger integer;
	private String cellEditor;
	
	
	public String getUi() {
		return ui;
	}
	@XmlElement
	public void setUi(String ui) {
		this.ui = ui;
	}
	
	
	public Choice getChoice() {
		return choice;
	}
	@XmlElement(name="choice")
	public void setNvChoice(Choice choice) {
		this.choice = choice;
	}
	
	
	public MyInteger getInteger() {
		return integer;
	}
	@XmlElement(name="integer")
	public void setNvInteger(MyInteger integer) {
		this.integer = integer;
	}
	
	
	public String getCellEditor() {
		return cellEditor;
	}
	@XmlElement(name="celleditor")
	public void setNvCellEditor(String cellEditor) {
		this.cellEditor = cellEditor;
	}
	
	public String getValue(Module module, int id, int bitmask) {
		if (module.getNvs() == null) return "NO NVs";
		Integer ip = module.getNvs().get(id);
		if (ip == null) return "No NV";
		Byte nvVal = ip.byteValue();
		byte b = (byte) (nvVal & bitmask);
		if (integer != null) {
			return ""+b;
		}
		if (choice != null) {
			for (Item nvi : choice.getItems()) {
				if (nvi.getValue() == b) return nvi.getSetting();
			}
			return "Invalid";
		}
		return "Unknown";
	}

}
