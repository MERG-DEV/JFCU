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
	@XmlElementWrapper(name="groups")
	@XmlElement(name="group")
	private List<Group> groups;
	private String name;

	public List<Group> getGroups() {
		return groups;
	}
	public void setNvGroups(List<Group> groups) {
		this.groups = groups;
	}
		
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
		
	@Override
	public String toString() {
		return "NvGroup [groups=" + groups + ", name=" + name + "]";
	}
}
