package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

public class VersionObservable implements ObservableStringValue {
	private ObservableIntegerValue mj;
	private ObservableIntegerValue mn;
	private List<ChangeListener<? super String>> listeners;
	
	public VersionObservable(ObservableIntegerValue mj, ObservableIntegerValue mn) {
		this.mj = mj;
		this.mn = mn;
		listeners = new ArrayList<ChangeListener<? super String>>();
		mj.addListener(new MjChangeListener(this));
		mn.addListener(new MnChangeListener(this));
	}

	@Override
	public String get() {
		return ""+mj.getValue().intValue()+(char)(mn.getValue().intValue());
	}


	@Override
	public void addListener(ChangeListener<? super String> l) {
		listeners.add(l);
	}

	@Override
	public String getValue() {
		return ""+mj.getValue().intValue()+(char)(mn.getValue().intValue());
	}

	@Override
	public void removeListener(ChangeListener<? super String> l) {
		listeners.remove(l);
	}

	@Override
	public void addListener(InvalidationListener arg0) {
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
	}
	
	private class MjChangeListener implements ChangeListener<Number>{
		private VersionObservable vo;
		public MjChangeListener(VersionObservable vo) {
			this.vo = vo;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(vo, ""+oldValue.intValue()+(char)(mn.getValue().intValue()), 
							""+newValue.intValue()+(char)(mn.getValue().intValue()));
				}
			}
			
		}
	}
	private class MnChangeListener implements ChangeListener<Number>{
		private VersionObservable vo;
		public MnChangeListener(VersionObservable vo) {
			this.vo = vo;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(vo, ""+mj.getValue().intValue()+(char)(oldValue.intValue()), 
							""+mj.getValue().intValue()+(char)newValue.intValue());
				}
			}
			
		}
	}

}
