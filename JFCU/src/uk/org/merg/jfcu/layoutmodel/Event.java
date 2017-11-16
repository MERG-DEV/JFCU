package uk.org.merg.jfcu.layoutmodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement
public class Event {
	public enum Etype {
		LONG,
		SHORT
	}
	private IntegerProperty id;
	private StringProperty name;
	private ObjectProperty<Etype> length;
	private IntegerProperty nn;
	private IntegerProperty en;
//	private MapProperty<Integer, Integer> evs;
	
	public Event() {
		id = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
		length = new SimpleObjectProperty<Etype>();
		nn = new SimpleIntegerProperty();
		en = new SimpleIntegerProperty();
//		evs = new SimpleMapProperty<Integer, Integer>();
	}
	
	
	public int getId() {
		return id.get();
	}
	@XmlElement
	public void setId(int id) {
		this.id.set(id);
	}
	public IntegerProperty idProperty() {
		return id;
	}
	
	
	public String getName() {
		return name.get();
	}
	@XmlElement
	public void setName(String name) {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	public Etype getLength() {
		return length.get();
	}
	public void setLength(Etype length) {
		this.length.set(length);
	}
	public ObjectProperty<Etype> lengthProperty() {
		return length;
	}
	
	
	public int getNn() {
		return nn.get();
	}
	@XmlElement
	public void setNn(int nn) {
		this.nn.set(nn);
	}
	public IntegerProperty nnProperty() {
		return nn;
	}
	
	
	public int getEn() {
		return en.get();
	}
	@XmlElement
	public void setEn(int en) {
		this.en.set(en);
	}
	public IntegerProperty enProperty() {
		return en;
	}
	
	
/*	public ObservableMap<Integer, Integer> getEvs() {
		return evs.get();
	}
	@XmlElement
	public void setEvs(ObservableMap<Integer, Integer> evs) {
		this.evs.set(evs);
	}
	public MapProperty<Integer,Integer> evsProperty() {
		return evs;
	}
	*/
	
	@Override 
	public boolean equals(Object o) {
		if (o instanceof Event) {
			Event e = (Event)o;
			if (en.get() != e.getEn()) return false;
			if (length.get() != e.getLength()) {
				System.out.println("WARNING mix of LONG and SHORT event with same EN");
				return false;
			}
			if (length.get() == Etype.SHORT) return true;
			if (nn.get() != e.getNn()) 	return false;
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return nn.get()*en.get()+(length.get()==Etype.LONG?1:0);
	}
}
