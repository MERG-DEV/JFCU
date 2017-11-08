package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class NvTab {
	@XmlElementWrapper(name="nvgroups")
	@XmlElement(name="nvgroup")
	private List<NvGroup> nvGroups;
	private String name;

	public List<NvGroup> getNvGroups() {
		return nvGroups;
	}
	public void setNvGroups(List<NvGroup> nvGroups) {
		this.nvGroups = nvGroups;
	}
		
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
		
	@Override
	public String toString() {
		return "NvGroup [groups=" + nvGroups + ", name=" + name + "]";
	}
}
