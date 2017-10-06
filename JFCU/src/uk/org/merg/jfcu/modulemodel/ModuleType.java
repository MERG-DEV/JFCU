package uk.org.merg.jfcu.modulemodel;

import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="moduletype")
@XmlAccessorType (XmlAccessType.FIELD)
public class ModuleType {
	private String name;
	private int id;
	@XmlElementWrapper(name="nvgroups")
	@XmlElement(name="nvgroup")
	private List<NvGroup> nvGroups;
	private HashMap<Short, ProducedAction> defaultProducedEvents;
	private HashMap<Short, ConsumedAction> defaultConsumedEvents;
	private String nvEditWindowPlugin;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public List<NvGroup> getNvGroups() {
		return nvGroups;
	}
	public void setNvGroups(List<NvGroup> nvGroups) {
		this.nvGroups = nvGroups;
	}
	
	
	public HashMap<Short, ProducedAction> getDefaultProducedEvents() {
		return defaultProducedEvents;
	}
	public void setDefaultProducedEvents(HashMap<Short, ProducedAction> defaultProducedEvents) {
		this.defaultProducedEvents = defaultProducedEvents;
	}
	
	
	public HashMap<Short, ConsumedAction> getDefaultConsumedEvents() {
		return defaultConsumedEvents;
	}
	public void setDefaultConsumedEvents(HashMap<Short, ConsumedAction> defaultConsumedEvents) {
		this.defaultConsumedEvents = defaultConsumedEvents;
	}
	
	public String getNvEditWindowPlugin() {
		return nvEditWindowPlugin;
	}
	public void setNvEditWindowPlugin(String nvEditWindowPlugin) {
		this.nvEditWindowPlugin = nvEditWindowPlugin;
	}
	
	@Override
	public String toString() {
		return "ModuleType [name=" + name + ", id=" + id + ", nvGroups=" + nvGroups + ", defaultProducedEvents="
				+ defaultProducedEvents + ", defaultConsumedEvents=" + defaultConsumedEvents + "]";
	}
}
