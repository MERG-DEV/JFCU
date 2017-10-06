package uk.org.merg.jfcu.modulemodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class NvInteger {
	private int nvMin;
	private int nvMax;
	
	
	public int getNvMin() {
		return nvMin;
	}
	public void setNvMin(int nvMin) {
		this.nvMin = nvMin;
	}
	
	
	public int getNvMax() {
		return nvMax;
	}
	public void setNvMax(int nvMax) {
		this.nvMax = nvMax;
	}
	
}
