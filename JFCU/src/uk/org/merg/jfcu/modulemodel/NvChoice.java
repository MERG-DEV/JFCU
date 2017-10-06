package uk.org.merg.jfcu.modulemodel;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class NvChoice {
	@XmlElement(name="nvitem")
	private List<NvItem>	nvitems;

	
	public List<NvItem> getNvitems() {
		return nvitems;
	}
	public void setNvitems(List<NvItem> nvitems) {
		this.nvitems = nvitems;
	}
	
	
}
