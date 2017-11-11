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
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
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
	private ReadOnlyIntegerWrapper numNv;
	private ReadOnlyIntegerWrapper numEvents;
	private SimpleMapProperty<Integer, Integer> params;
	private SimpleMapProperty<Integer, Integer> nvs;
	private SimpleSetProperty<Event> events;
	private VersionObservable vo;
	
	
	public Module() {
		name = new SimpleStringProperty();
		nodeNumber = new SimpleIntegerProperty();
		canid = new SimpleIntegerProperty();
		
		params = new SimpleMapProperty<Integer, Integer>(FXCollections.observableHashMap());
		// create necessary dummy values
		for (int i=0; i<CbusProperties.MAX.getValue(); i++) {
			params.put(i, 0);
		}
		
		// default to Microchip
		params.get().put(CbusProperties.PROC_MANU.getValue(), 1);
		moduleTypeName = new ReadOnlyStringWrapper();
		moduleTypeName.bind(new ModuleObservable(Bindings.integerValueAt(params, CbusProperties.MODULEID.getValue())));
		fullVersion = new ReadOnlyStringWrapper();
		vo = new VersionObservable(Bindings.integerValueAt(params, new Integer(CbusProperties.MAJOR_VERSION.getValue())),
				Bindings.integerValueAt(params, new Integer(CbusProperties.MINOR_VERSION.getValue())));
		fullVersion.bind(vo);
		processor = new ReadOnlyStringWrapper();
		processor.bind(new ProcessorObservable(Bindings.integerValueAt(params, new Integer(CbusProperties.PROC_MANU.getValue())), 
				Bindings.integerValueAt(params, new Integer(CbusProperties.PROCESSOR.getValue()))));
		flimFlag = new ReadOnlyBooleanWrapper();
		flimFlag.bind(new BitObservable(Bindings.integerValueAt(params, new Integer(CbusProperties.FLAGS.getValue())), 4));
		numNv = new ReadOnlyIntegerWrapper();
		numNv.bind(Bindings.integerValueAt(params, new Integer(CbusProperties.NUM_NV.getValue())));
		numEvents = new ReadOnlyIntegerWrapper();
		numEvents.bind(Bindings.integerValueAt(params, new Integer(CbusProperties.NUMEVENTS.getValue())));
		nvs = new SimpleMapProperty<Integer, Integer>(FXCollections.observableHashMap());
		events = new SimpleSetProperty<Event>(FXCollections.observableSet());
	}

	/* name */
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
	
	
	/* fullVersion */
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
	
	
	/* flimFlag */
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
	
	
	/* processor */
	public String getProcessor() {
		return processor.get();
	}
	@XmlElement
	public void setProcessor(String processor) {
		// disallowed
	}
	public ReadOnlyStringProperty processorProperty() {
		return processor;
	}
	
	
	/* nodeNumber */
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
	
	
	/* canId */
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
	
	
	/* moduleTypeName */
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
		
	
	/* params */
	public ObservableMap<Integer, Integer> getParams() {
		return params.get();
	}
	@XmlElement
	public void setParams(Map<Integer, Integer> params) {
		this.params.clear();
		this.params.putAll(params);
	}
	public MapProperty<Integer,Integer> paramsProperty() {
		return params;
	}
	
	
	/* nvs */
	public Map<Integer, Integer> getNvs() {
		return nvs.get();
	}
	@XmlElement
	public void setNvs(Map<Integer, Integer> nvs) {
		this.nvs.clear();
		this.nvs.putAll(nvs);
	}
	public MapProperty<Integer,Integer> nvsProperty() {
		return nvs;
	}
	
	
	/* numNv */
	public int getNumNv() {
		return numNv.get();
	}
	@XmlElement
	public void setNumNv(Integer nnv) {
		// not allowed
	}
	public ReadOnlyIntegerProperty numNvProperty() {
		return numNv;
	}
	
	
	/* numEvents */
	public int getNumEvents() {
		return numEvents.get();
	}
	@XmlElement
	public void setNumEvents(Integer nnv) {
		// not allowed
	}
	public ReadOnlyIntegerProperty numEventsProperty() {
		return numEvents;
	}
	
	
	/* events */
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
				", Version="+fullVersion+
				"]";
	}
	
}
