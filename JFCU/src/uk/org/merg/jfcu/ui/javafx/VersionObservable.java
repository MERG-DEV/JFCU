package uk.org.merg.jfcu.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

public class VersionObservable implements ObservableStringValue, InvalidationListener {
	private ObservableIntegerValue mj;
	private ObservableIntegerValue mn;
	private List<ChangeListener<? super String>> listeners;
	private List<InvalidationListener> invlisteners;
	
	public VersionObservable(ObservableIntegerValue mj, ObservableIntegerValue mn) {
		this.mj = mj;
		this.mn = mn;
		listeners = new ArrayList<ChangeListener<? super String>>();
		invlisteners = new ArrayList<InvalidationListener>();
		mj.addListener(new MjChangeListener(this));
		mj.addListener(this);
		mn.addListener(new MnChangeListener(this));
		mn.addListener(this);
	}

	@Override
	public String get() {
		return ""+mj.get()+(char)(mn.get());
	}


	@Override
	public void addListener(ChangeListener<? super String> l) {
		listeners.add(l);
	}

	@Override
	public String getValue() {
		return ""+mj.get()+(char)(mn.get());
	}

	@Override
	public void removeListener(ChangeListener<? super String> l) {
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
	
	private class MjChangeListener implements ChangeListener<Number>{
		private VersionObservable vo;
		public MjChangeListener(VersionObservable vo) {
			this.vo = vo;
		}
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//			System.out.println("See a major change to "+newValue);
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
					l.changed(vo, ""+oldValue.intValue()+(char)(mn.get()), ""+newValue.intValue()+(char)(mn.get()));
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
//			System.out.println("See a minor change to "+newValue);
			if (oldValue.intValue() != newValue.intValue()) {
				for (ChangeListener<? super String> l : listeners) {
//					System.out.println("informing "+l);
					l.changed(vo, ""+mj.get()+(char)(oldValue.intValue()), ""+mj.get()+(char)newValue.intValue());
				}
			}
			
		}
	}
	@Override
	public void invalidated(Observable observable) {
		for (InvalidationListener l : invlisteners) {
//			System.out.println("informing "+l);
			l.invalidated(this);
		}
	}

}
