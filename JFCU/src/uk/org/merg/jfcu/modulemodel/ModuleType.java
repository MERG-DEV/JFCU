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
	@XmlElementWrapper(name="nvtabs")
	@XmlElement(name="tab")
	private List<NvTab> tabs;
	private HashMap<Short, ProducedAction> defaultProducedEvents;
	private HashMap<Short, ConsumedAction> defaultConsumedEvents;
	private String nvEditWindowPlugin;
	
	public List<NvTab> getTabs() {
		return tabs;
	}
	public void setTabs(List<NvTab> t) {
		this.tabs = t;
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
		return "ModuleType [ tabs=" + tabs + ", defaultProducedEvents="
				+ defaultProducedEvents + ", defaultConsumedEvents=" + defaultConsumedEvents + "]";
	}
}
