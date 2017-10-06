package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NvByte {
	private int id;
	private List<NvBits> nvbits;

	
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	
	
	public List<NvBits> getNvbits() {
		return nvbits;
	}
	@XmlElement
	public void setNvbits(List<NvBits> nvbits) {
		this.nvbits = nvbits;
	}
	
	
	@Override
	public String toString() {
		return "Nv [bits=" + nvbits + ", id=" + id + "]";
	}
	
}
