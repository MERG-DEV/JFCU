package uk.org.merg.jfcu.layoutmodel;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

@XmlRootElement
public class Module {
	private StringProperty name;
	private IntegerProperty moduleTypeId;
	private IntegerProperty nodeNumber;
	private IntegerProperty canid;
	private StringProperty moduleTypeName;
	private IntegerProperty numNV;
	private StringProperty version;
	private StringProperty subVersion;
	private ReadOnlyStringWrapper fullVersion;
	private SimpleMapProperty<Integer,Byte> params;
	private SimpleMapProperty<Integer, Byte> nvs;
	private SimpleSetProperty<Event> events;
	
	public Module() {
		name = new SimpleStringProperty();
		moduleTypeId = new SimpleIntegerProperty();
		nodeNumber = new SimpleIntegerProperty();
		canid = new SimpleIntegerProperty();
		moduleTypeName = new SimpleStringProperty();
		numNV = new SimpleIntegerProperty();
		version = new SimpleStringProperty();
		subVersion = new SimpleStringProperty();
		fullVersion = new ReadOnlyStringWrapper();
		fullVersion.bind(Bindings.concat(version,subVersion));
		params = new SimpleMapProperty<Integer, Byte>(FXCollections.observableHashMap());
		nvs = new SimpleMapProperty<Integer, Byte>(FXCollections.observableHashMap());
		events = new SimpleSetProperty<Event>(FXCollections.observableSet());
	}

	public String getName() {
		return name.get();
	}
	@XmlElement
	public void setName(String name) {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	
	public int getModuleTypeId() {
		return moduleTypeId.get();
	}
	@XmlElement(name="moduletypeid")
	public void setModuleTypeId(int id) {
		this.moduleTypeId.set(id);
	}
	public IntegerProperty moduleTypeIdProperty() {
		return moduleTypeId;
	}
	
	public String getVersion() {
		return version.get();
	}
	@XmlElement
	public void setVersion(String version) {
		this.version.set(version);
	}
	public StringProperty versionProperty() {
		return version;
	}

	public String getSubVersion() {
		return subVersion.get();
	}
	@XmlElement
	public void setSubVersion(String subVersion) {
		this.subVersion.set(subVersion);
	}
	public StringProperty subVersionProperty() {
		return subVersion;
	}
	
	public String getFullVersion() {
		return fullVersion.get();
	}
	@XmlElement
	public void setFullVersion(String fullVersion) {
		// disallowed
	}
	public ReadOnlyStringProperty fullVersionProperty() {
		return fullVersion;
	}
	
	
	public int getNodeNumber() {
		return nodeNumber.get();
	}
	@XmlElement(name="nodenumber")
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber.set(nodeNumber);
	}
	public IntegerProperty nodeNumberProperty() {
		return nodeNumber;
	}
	
	
	public int getCanid() {
		return nodeNumber.get();
	}
	@XmlElement(name="canid")
	public void setCanid(int canid) {
		this.canid.set(canid);
	}
	public IntegerProperty canidProperty() {
		return canid;
	}
	
	
	public String getModuleTypeName() {
		return moduleTypeName.get();
	}
	@XmlElement(name="moduletypename")
	public void setModuleTypeName(String moduleTypeName) {
		this.moduleTypeName.set(moduleTypeName);
	}
	public StringProperty moduleTypeNameProperty() {
		return moduleTypeName;
	}
		
	
	public int getNumNV() {
		return numNV.get();
	}
	@XmlElement(name="numnv")
	public void setNumNV(int numNV) {
		this.numNV.set(numNV);
	}
	public IntegerProperty numNVProperty() {
		return numNV;
	}
	
	
	public Map<Integer, Byte> getParams() {
		return params.get();
	}
	@XmlElement
	public void setParams(Map<Integer, Byte> params) {
		this.params.clear();
		this.params.putAll(params);
	}
	public MapProperty<Integer,Byte> paramsProperty() {
		return params;
	}
	
	
	public Map<Integer, Byte> getNvs() {
		return nvs.get();
	}
	@XmlElement
	public void setNvs(Map<Integer, Byte> nvs) {
		this.nvs.clear();
		this.nvs.putAll(nvs);
	}
	public MapProperty<Integer,Byte> nvsProperty() {
		return nvs;
	}
	
	
	public Set<Event> getEvents() {
		return events.get();
	}
	@XmlElement
	public void setEvents(Set<Event> events) {
		this.events.clear();
		this.events.addAll(events);
	}
	public SetProperty<Event> eventsProperty() {
		return events;
	}

	@Override
	public String toString() {
		return "Module [name=" + name + ", moduleTypeId=" + moduleTypeId + ", nodeNumber=" + nodeNumber
				+ ", moduleTypeName=" + moduleTypeName + ", numNV=" + numNV + ", version=" + version + ", subVersion="+subVersion+", params="
				+ params + ", nvs=" + nvs + ", events=" + events + "]";
	}
	
}
