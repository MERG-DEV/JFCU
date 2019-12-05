package uk.org.merg.jfcu.modulemodel;

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
	@XmlElement(name="nvtab")
	private List<NvTab> nvTabs;
	
	@XmlElementWrapper(name="producedevents")
	@XmlElement(name="evtab")
	private List<EvTab> producedTabs;
	
	@XmlElementWrapper(name="consumedevents")
	@XmlElement(name="evtab")
	private List<EvTab> consumedTabs;
	
	private String nvEditWindowPlugin;
	
	public List<NvTab> getNvTabs() {
		return nvTabs;
	}
	public void setNvTabs(List<NvTab> t) {
		this.nvTabs = t;
	}
	
	
	public List<EvTab> getProducedEvents() {
		return producedTabs;
	}
	public void setProducedEvents(List<EvTab> et) {
		this.producedTabs = et;
	}
	
	public List<EvTab> getConsumedEvents() {
		return consumedTabs;
	}
	public void setConsumedEvents(List<EvTab> et) {
		this.consumedTabs = et;
	}
	
	
	public String getNvEditWindowPlugin() {
		return nvEditWindowPlugin;
	}
	public void setNvEditWindowPlugin(String nvEditWindowPlugin) {
		this.nvEditWindowPlugin = nvEditWindowPlugin;
	}
	
	@Override
	public String toString() {
		return "ModuleType [ nvTabs=" + nvTabs + ", producedTabs=" + producedTabs + ", consumedTabs=" + consumedTabs + "]";
	}
}
