package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;

public class BitObservable implements ObservableBooleanValue, InvalidationListener {
	private ObservableIntegerValue oi;
	private int mask;
	private List<ChangeListener<? super Boolean>> listeners;
	private List<InvalidationListener> invlisteners;
	
	public BitObservable(ObservableIntegerValue oi, int mask) {
		this.oi = oi;
		oi.addListener(new BitChangeListener(this));
		oi.addListener(this);
		this.mask = mask;
		listeners = new ArrayList<ChangeListener<? super Boolean>>();
		invlisteners = new ArrayList<InvalidationListener>();
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
	public void addListener(InvalidationListener l) {
		invlisteners.add(l);
	}
	@Override
	public void removeListener(InvalidationListener l) {
		invlisteners.remove(l);
	}
	@Override
	public boolean get() {
		return (oi.getValue().intValue() & mask) != 0;
	}
	
	
	private class BitChangeListener implements ChangeListener<Number>{
		private BitObservable bo;
		public BitChangeListener(BitObservable bo) {
			this.bo = bo;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super Boolean> l : listeners) {
					l.changed(bo, (oldValue.intValue()&mask) != 0, (newValue.intValue()&mask) != 0);
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
