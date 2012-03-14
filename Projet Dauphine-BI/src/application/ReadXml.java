package application;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import entity.Input;

public class ReadXml {
	
	private String xmlFileName = "dataSource/input.xml";
	
	private Input input;
	
	
	public static String getResourcePath(String fileName) {
		final File f = new File("");
		final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
		return dossierPath;
	}

	public String getXmlFileName() {
		return xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public static File getResource (String fileName){
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		return file;
	}
	/**
	 * Constructeur de la classe ReadXml. Construit directement les objets
	 */
	public ReadXml() {
		try{
			JAXBContext contexte = JAXBContext.newInstance(Input.class);
			Unmarshaller mapperXMLObjet = contexte.createUnmarshaller();
			File f = getResource(xmlFileName);
			//System.out.println(getResource(xmlFileName));
			input = (Input) mapperXMLObjet.unmarshal(f);
			
		}
		catch (JAXBException e) { 
			traceError("Error while reading the file [" + xmlFileName + "] : "+ e.getMessage(), e);  
		}
	}

	private void traceError(String error, JAXBException e) {
		
		System.out.println(error);
		e.printStackTrace();
		
	}

	
	
	

	
	
}
