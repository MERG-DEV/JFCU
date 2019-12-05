package uk.org.merg.jfcu.modulemodel;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Choice {
	@XmlElement(name="item")
	private List<Item> items;

	
	public List<Item> getItems() {
		return items;
	}
	public void setNvitems(List<Item> items) {
		this.items = items;
	}
	
	
}
