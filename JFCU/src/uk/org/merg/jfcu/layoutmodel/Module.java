package uk.org.merg.jfcu.layoutmodel;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import uk.org.merg.jfcu.cbus.cbusdefs.CbusProperties;
import uk.org.merg.jfcu.ui.javafx.BitObservable;
import uk.org.merg.jfcu.ui.javafx.ModuleObservable;
import uk.org.merg.jfcu.ui.javafx.ProcessorObservable;
import uk.org.merg.jfcu.ui.javafx.VersionObservable;

@XmlRootElement
public class Module {
	private StringProperty name;
	private IntegerProperty nodeNumber;
	private IntegerProperty canid;
	private StringProperty moduleTypeName;
	private ReadOnlyStringWrapper fullVersion;
	private ReadOnlyStringWrapper processor;
	private ReadOnlyBooleanWrapper flimFlag;
	private SimpleMapProperty<Integer, IntegerProperty> params;
	private SimpleMapProperty<Integer, IntegerProperty> nvs;
	private SimpleSetProperty<Event> events;
	
	
	public Module() {
		name = new SimpleStringProperty();
		nodeNumber = new SimpleIntegerProperty();
		canid = new SimpleIntegerProperty();
		
		params = new SimpleMapProperty<Integer, IntegerProperty>(FXCollections.observableHashMap());
		// create necessary dummy values
		for (int i=0; i<CbusProperties.MAX.getValue(); i++) {
			params.put(i, new SimpleIntegerProperty());
		}
		
		// default to Microchip
		params.get().get(CbusProperties.PROC_MANU.getValue()).setValue(1);
		moduleTypeName = new ReadOnlyStringWrapper();
		moduleTypeName.bind(new ModuleObservable(params.get(CbusProperties.MODULEID.getValue())));
		fullVersion = new ReadOnlyStringWrapper();
		fullVersion.bind(new VersionObservable(params.get(CbusProperties.MAJOR_VERSION.getValue()),params.get(CbusProperties.MINOR_VERSION.getValue())));
		processor = new ReadOnlyStringWrapper();
		processor.bind(new ProcessorObservable(params.get(CbusProperties.PROC_MANU.getValue()), params.get(CbusProperties.PROCESSOR.getValue())));
		flimFlag = new ReadOnlyBooleanWrapper();
		flimFlag.bind(new BitObservable(params.get(CbusProperties.FLAGS.getValue()), 4));
		nvs = new SimpleMapProperty<Integer, IntegerProperty>(FXCollections.observableHashMap());
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
	
	public Boolean getFlimFlag() {
		return flimFlag.get();
	}
	@XmlElement
	public void setFlimFlag(Boolean ff) {
		// disallowed
	}
	public ReadOnlyBooleanProperty flimFlagProperty() {
		return flimFlag;
	}
	
	public String getProcessorn() {
		return processor.get();
	}
	@XmlElement
	public void setProcessor(String processor) {
		// disallowed
	}
	public ReadOnlyStringProperty processorProperty() {
		return processor;
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
		// not allowed
	}
	public StringProperty moduleTypeNameProperty() {
		return moduleTypeName;
	}
		
	
	
	public ObservableMap<Integer, IntegerProperty> getParams() {
		return params.get();
	}
	@XmlElement
	public void setParams(Map<Integer, IntegerProperty> params) {
		this.params.clear();
		this.params.putAll(params);
	}
	public MapProperty<Integer,IntegerProperty> paramsProperty() {
		return params;
	}
	
	
	public Map<Integer, IntegerProperty> getNvs() {
		return nvs.get();
	}
	@XmlElement
	public void setNvs(Map<Integer, IntegerProperty> nvs) {
		this.nvs.clear();
		this.nvs.putAll(nvs);
	}
	public MapProperty<Integer,IntegerProperty> nvsProperty() {
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
		return "Module [name=" + name + 
				", nodeNumber=" + nodeNumber + 
				", moduleTypeName=" + moduleTypeName + 
				", params=" + params + 
				", nvs=" + nvs + 
				", events=" + events + 
				", processor="+processor+
				", Flim="+flimFlag+
				"]";
	}
	
}
