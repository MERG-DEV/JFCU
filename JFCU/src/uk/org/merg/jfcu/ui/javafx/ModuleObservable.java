package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import uk.org.merg.jfcu.cbus.Globals;

public class ModuleObservable implements ObservableValue<String>, InvalidationListener {
	private List<ChangeListener<? super String>> listeners;
	private List<InvalidationListener> invlisteners;
	private ObservableIntegerValue oi;
	
	public ModuleObservable(ObservableIntegerValue oi) {
		listeners = new ArrayList<ChangeListener<? super String>>();
		invlisteners = new ArrayList<InvalidationListener>();
		this.oi = oi;
		oi.addListener(new ModuleChangeListener(this));
		oi.addListener(this);
	}
	@Override
	public void addListener(InvalidationListener l) {
		invlisteners.add(l);
	}

	@Override
	public void removeListener(InvalidationListener l) {
		invlisteners.remove(l);
	}

	@Override
	public void addListener(ChangeListener<? super String> l) {
		listeners.add(l);
	}

	@Override
	public String getValue() {
		return Globals.moduleTypeNames.find(oi.intValue());
	}

	@Override
	public void removeListener(ChangeListener<? super String> l) {
		listeners.remove(l);
	}
	
	private class ModuleChangeListener implements ChangeListener<Number>{
		private ModuleObservable mo;
		public ModuleChangeListener(ModuleObservable mo) {
			this.mo = mo;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(mo, Globals.moduleTypeNames.find(oldValue.intValue()), Globals.moduleTypeNames.find(newValue.intValue()));
				}
			}
			
		}
	}
	
	@Override
	public void invalidated(Observable observable) {
		for (InvalidationListener l : invlisteners) {
			l.invalidated(this);
		}
	}

}
