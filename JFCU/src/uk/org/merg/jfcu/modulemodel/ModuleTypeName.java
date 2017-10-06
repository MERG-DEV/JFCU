package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModuleTypeName {
	private int moduleId;
	private String moduleType;
	
	public int getModuleId() {
		return moduleId;
	}
	@XmlElement(name="moduleid")
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	
	public String getModuleType() {
		return moduleType;
	}
	@XmlElement(name="moduletype")
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	
	
	@Override
	public String toString() {
		return "ModuleTypeName [moduleId=" + moduleId + ", moduleType=" + moduleType + "]";
	}
	
	
}
