package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class NvGroup {
	@XmlElementWrapper(name="nvs")
	@XmlElement(name="nvbyte")
	private List<NvByte> nvs;
	private String name;

	public List<NvByte> getNvs() {
		return nvs;
	}
	public void setNvs(List<NvByte> nvs) {
		this.nvs = nvs;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "NvGroup [nvs=" + nvs + ", name=" + name + "]";
	}
	
	
}
