package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Group {
	@XmlElementWrapper(name="bytes")
	@XmlElement(name="byte")
	private List<MyByte> myBytes;
	private String name;

	public List<MyByte> getBytes() {
		return myBytes;
	}
	public void setBytes(List<MyByte> myBytes) {
		this.myBytes = myBytes;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "NvGroup [myBytes=" + myBytes + ", name=" + name + "]";
	}
	
	
}
