package uk.org.merg.jfcu.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import uk.org.merg.jfcu.modulemodel.ModuleType;

public class ModuleTypeDataCache {
	private static Map<String, ModuleType> cache = new HashMap<String, ModuleType>();
	
	/**
	 * Try to get the moduleType. First try using the full name, version and subVersion. If we
	 * don't get a match then just try the name and version and then try just the name.
	 * 
	 * @param name
	 * @param mj
	 * @param mn
	 * @return
	 */
	public static ModuleType get(String name, int mj, char mn) {
		ModuleType mt;
		mt = get(name+"v"+mj+mn);
		if (mt != null) return mt;
		mt = get(name+"v"+mj);
		if (mt != null) return mt;
		mt = get(name);
		return mt;
	}
	
	public static ModuleType get(String name) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		}
		URL url = ModuleTypeDataCache.class.getResource("/modules/"+name+".xml");
		if (url == null) return null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ModuleType.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ModuleType moduleType = (ModuleType) jaxbUnmarshaller.unmarshal(url);
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
			System.out.println("NAME="+name+"="+moduleType);
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
			if (e.getLinkedException() instanceof FileNotFoundException) return null;
			e.printStackTrace();
		} 
		return null;
	}
}
