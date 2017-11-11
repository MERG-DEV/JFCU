package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import uk.org.merg.jfcu.cbus.Globals;

public class ModuleObservable implements ObservableValue<String>, ChangeListener<Integer> {
	private List<ChangeListener<? super String>> listeners;
	private ObservableIntegerValue oi;
	
	public ModuleObservable(ObservableIntegerValue oi) {
		listeners = new ArrayList<ChangeListener<? super String>>();
		this.oi = oi;
	}
	@Override
	public void addListener(InvalidationListener arg0) {
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
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

	@Override
	public void changed(ObservableValue<? extends Integer> ov, Integer oldValue, Integer newValue) {
		if (oldValue != newValue) {
			for (ChangeListener<? super String> l : listeners) {
				l.changed(this, Globals.moduleTypeNames.find(oldValue.intValue()), Globals.moduleTypeNames.find(newValue.intValue()));
			}
		}
		
	}

}
