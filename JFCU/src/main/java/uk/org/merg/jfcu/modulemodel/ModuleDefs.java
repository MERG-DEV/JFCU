package uk.org.merg.jfcu.modulemodel;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.modulemodel.ModuleTypeNames;


public class ModuleDefs {

	public static void loadModuleDefinitions() {
		URL url = ModuleDefs.class.getResource(
                 "/config/modulenames.xml");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ModuleTypeNames.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Globals.moduleTypeNames = (ModuleTypeNames) jaxbUnmarshaller.unmarshal(url);
			System.out.println("Unmarshalled modulenames.xml ="+Globals.moduleTypeNames);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	

}
