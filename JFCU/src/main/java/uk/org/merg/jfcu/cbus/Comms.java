package uk.org.merg.jfcu.cbus;

import javax.swing.text.BadLocationException;
import co.uk.ccmr.cbus.driver.CbusDriver;
import co.uk.ccmr.cbus.driver.CbusDriverException;
import co.uk.ccmr.cbus.util.OptionsImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import uk.org.merg.jfcu.cbus.ResponseHandler;


public class Comms {
	
	public static CbusDriver theDriver;
	private static ResponseHandler responseHandler;
	public static StringProperty portName = new SimpleStringProperty();
		
	public static void prepareCommsDriver(String [] args) {
		// try to load the driver
		String clazz = OptionsImpl.getOptions(Globals.log, args).getDriver();
		Class<CbusDriver> clz = null;
		try {
			clz = (Class<CbusDriver>) Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("No class:"+clazz);
			System.exit(1);
		}
		if (clz == null) {
			System.err.println("No class:"+clazz);
			System.exit(1);
		}
		try {
			theDriver = clz.newInstance();
			theDriver.init(0, Globals.log, OptionsImpl.getOptions(Globals.log, args));
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.err.println("Can't instantiate class:"+clazz);
			System.exit(1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.err.println("Can't instantiate class:"+clazz);
			System.exit(1);
		}
		if (theDriver == null) {
			System.err.println("Can't instantiate class:"+clazz);
			System.exit(1);
		} else {
			System.out.println("Using driver "+clazz);
			try {
				Globals.log.insertString(0, "Using driver "+clazz+"\n", Globals.redAset);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		portName.set(OptionsImpl.getOptions(Globals.log, args).getAutoConnect());
		if (portName.get() != null) {
			try {
				Globals.log.insertString(0, "Auto Opening port "+portName+"\n", Globals.redAset);
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			
			
			try {
				theDriver.connect(portName.get());
			} catch (CbusDriverException e2) {
				e2.printStackTrace();
				try {
					Globals.log.insertString(0, e2.getMessage()+"\n", null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		} else {
			portName.set("None");
		}
		responseHandler = new ResponseHandler();
		theDriver.addListener(responseHandler);
	}
	
	public static void connect(String port) {
		try {
			Globals.log.insertString(0, "Opening port "+Comms.portName+"\n", Globals.redAset);
		} catch (BadLocationException e2) {
			e2.printStackTrace();
		}
		System.out.println("Opening port \""+port+"\"");
		
		
		try {
			theDriver.connect(port);
			Comms.portName.set(port);
		} catch (CbusDriverException e2) {
			e2.printStackTrace();
			try {
				Globals.log.insertString(0, e2.getMessage(), null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close() {
		theDriver.close();
	}
}