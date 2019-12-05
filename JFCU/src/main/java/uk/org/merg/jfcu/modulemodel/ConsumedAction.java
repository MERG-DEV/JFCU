package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsumedAction {
	private int action;

	public int getAction() {
		return action;
	}
	@XmlElement
	public void setAction(int action) {
		this.action = action;
	}
}
