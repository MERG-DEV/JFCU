package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;

public class BitObservable implements ObservableBooleanValue, ChangeListener<Number> {
	private ObservableIntegerValue oi;
	private int mask;
	private List<ChangeListener<? super Boolean>> listeners;
	
	public BitObservable(ObservableIntegerValue oi, int mask) {
		this.oi = oi;
		oi.addListener(this);
		this.mask = mask;
		listeners = new ArrayList<ChangeListener<? super Boolean>>();
	}
	@Override
	public void addListener(ChangeListener<? super Boolean> l) {
		listeners.add(l);
	}
	@Override
	public Boolean getValue() {
		return (oi.getValue().intValue() & mask) != 0;
	}
	@Override
	public void removeListener(ChangeListener<? super Boolean> l) {
		listeners.remove(l);
	}
	@Override
	public void addListener(InvalidationListener arg0) {
	}
	@Override
	public void removeListener(InvalidationListener arg0) {		
	}
	@Override
	public boolean get() {
		return (oi.getValue().intValue() & mask) != 0;
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if ((oldValue.intValue()&mask) != (newValue.intValue()&mask)) {
			for (ChangeListener<? super Boolean>l : listeners) {
				l.changed(this, (oldValue.intValue()&mask) != 0, (newValue.intValue()&mask) != 0);
			}
		}
		
	}

}
