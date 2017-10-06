package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NvBits {
	private int bitmask;
	private String name;
	private String description;
	private NvType nvType;
	
	
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
	
	
	public NvType getNvType() {
		return nvType;
	}
	@XmlElement(name="nvtype")
	public void setNvType(NvType nvType) {
		this.nvType = nvType;
	}
	
	@Override
	public String toString() {
		return "NvBits [bitmask=" + bitmask + ", name=" + name + ", description=" + description + ", nvType=" + nvType
				+ "]";
	}
}
