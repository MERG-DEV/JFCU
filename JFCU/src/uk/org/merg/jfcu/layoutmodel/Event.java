package uk.org.merg.jfcu.layoutmodel;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
	private int id;
	private String name;
	private short nn;
	private short en;
	private HashMap<Integer, Byte> evs;
	
	
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	
	public short getNn() {
		return nn;
	}
	@XmlElement
	public void setNn(short nn) {
		this.nn = nn;
	}
	
	
	public short getEn() {
		return en;
	}
	@XmlElement
	public void setEn(short en) {
		this.en = en;
	}
	
	
	public HashMap<Integer, Byte> getEvs() {
		return evs;
	}
	@XmlElement
	public void setEvs(HashMap<Integer, Byte> evs) {
		this.evs = evs;
	}
	
}
