package uk.org.merg.jfcu.layoutmodel;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

import co.uk.ccmr.cbus.sniffer.CbusEvent;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MinPri;
import co.uk.ccmr.cbus.sniffer.CbusEvent.MjPri;
import co.uk.ccmr.cbus.sniffer.Opc;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.org.merg.jfcu.cbus.Comms;
import uk.org.merg.jfcu.cbus.Globals;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Layout {
	private String description;
	private int version;
	
	@XmlElementWrapper(name="modules")
	@XmlElement(name="module")
	private ListProperty<Module> modules;
	
	@XmlElementWrapper(name="events")
	@XmlElement(name="event")
	private ListProperty<Event> events;
	
	public Layout() {
		modules = new SimpleListProperty<Module>(FXCollections.observableArrayList());
		events = new SimpleListProperty<Event>(FXCollections.observableArrayList());
		description = "";
		version = 0;
	}
	
	@Override
	public String toString() {
		String s = "Layout: desc=\""+description+"\" version="+version;
		s+= "\nmodules="+modules;
		return s;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
	public List<Module> getModules() {
		return modules.get();
	}
	public ObservableList<Module> getModulesProperty() {
		return modules;
	}
	
	public void setModules(List<Module> modules) {
		this.modules.setAll(modules);
	}
	
	
	public List<Event> getEvents() {
		return events.get();
	}
	public void setEvents(List<Event> events) {
		this.events.setAll(events);
	}
	public ListProperty<Event> eventsProperty() {
		return events;
	}

	public void loadFromCBUS() {
		// Send a QNN to trigger process to get all the module information
		CbusEvent msg = new CbusEvent();
		msg.setMinPri(MinPri.HIGH);
		msg.setMjPri(MjPri.HIGH);
		msg.setCANID(Globals.CANID);
		msg.setOpc(Opc.QNN);
		
		Comms.theDriver.queueForTransmit(msg);	
	}

	public Module findModule(int nn) {
		for (Module m : modules) {
			if (m.getNodeNumber() == nn) return m;
		}
		return null;
	}
	
	public void save(String filename) {
		try {
			File file = new File(filename);
			JAXBContext jaxbContext = JAXBContext.newInstance(Layout.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(this, file);
			jaxbMarshaller.marshal(this, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static Layout load(String filename) {
		File file = new File(filename);
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Layout.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Layout layout = (Layout) jaxbUnmarshaller.unmarshal(file);
			System.out.println(layout);
			return layout;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
