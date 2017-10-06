package uk.org.merg.jfcu.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import uk.org.merg.jfcu.modulemodel.ModuleType;

public class ModuleTypeDataCache {
	private static Map<String, ModuleType> cache = new HashMap<String, ModuleType>();
	
	public static ModuleType get(String name) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		}
		File file = new File("modules/"+name+".xml");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ModuleType.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ModuleType moduleType = (ModuleType) jaxbUnmarshaller.unmarshal(file);
//			Nv nv = new Nv();
//			nv.setDescription("An Nv");
//			nv.setName("NV#1");
//			nv.setId(1);
//			NvType nvType = new NvType();
//			NvInteger nvInteger = new NvInteger();
//			nvInteger.setNvMax(255);
//			nvInteger.setNvMin(0);
//			nvType.setNvInteger(nvInteger);
//			nv.setNvType(nvType);
//			moduleType.getNvGroups().iterator().next().getNvs().add(nv);
			System.out.println(moduleType);
//			try {
//				JAXBContext jaxbContext2 = JAXBContext.newInstance(ModuleType.class);
//				Marshaller jaxbMarshaller = jaxbContext2.createMarshaller();
//
//				// output pretty printed
//				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//				jaxbMarshaller.marshal(moduleType, System.out);
//
//			} catch (JAXBException e) {
//				e.printStackTrace();
//			}
			
			cache.put(name, moduleType);
			return moduleType;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
