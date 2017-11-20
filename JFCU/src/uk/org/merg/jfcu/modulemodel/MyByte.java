package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyByte {
	private int id;
	private List<Bits> bits;

	
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	
	
	public List<Bits> getBits() {
		return bits;
	}
	@XmlElement
	public void setBits(List<Bits> bits) {
		this.bits = bits;
	}
	
	
	@Override
	public String toString() {
		return "Nv [bits=" + bits + ", id=" + id + "]";
	}
	
}
