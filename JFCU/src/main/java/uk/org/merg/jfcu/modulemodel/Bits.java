package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Bits {
	private int bitmask = 0xFF;
	private String name;
	private String description;
	private Type type;
	
	
	public int getBitmask() {
		return bitmask;
	}
	@XmlElement
	public void setBitmask(int bitmask) {
		this.bitmask = bitmask;
	}
	
	
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Type getType() {
		return type;
	}
	@XmlElement(name="type")
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Bits [bitmask=" + bitmask + ", name=" + name + ", description=" + description + ", type=" + type
				+ "]";
	}
}
