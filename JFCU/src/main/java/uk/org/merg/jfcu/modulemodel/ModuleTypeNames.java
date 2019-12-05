package uk.org.merg.jfcu.modulemodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="moduletypes")
@XmlAccessorType (XmlAccessType.FIELD)
public class ModuleTypeNames {
	@XmlElementWrapper(name="names")
	@XmlElement(name="name")
	private List<ModuleTypeName> names;

	public List<ModuleTypeName> getNames() {
		return names;
	}
	
	public void setNames(List<ModuleTypeName> names) {
		this.names = names;
	}

	public String find(int id) {
		for (ModuleTypeName mt: names) {
			if (mt.getModuleId() == id) return mt.getModuleType();
		}
		return null;
	}

	@Override
	public String toString() {
		return "ModuleTypeNames [names=" + names + "]";
	}
	
}
