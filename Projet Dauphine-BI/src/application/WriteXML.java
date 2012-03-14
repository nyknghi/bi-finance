package application;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import entity.Input;
import entityOutput.Output;

public class WriteXML {
	
	private String xmlFileName = "dataSource/output.xml";
	
	private Output output;
	


	public Output getOutput() {
		return output;
	}

	public void setInput(Output output) {
		this.output = output;
	}

	/**
	 * Constructeur de la classe WriteXml. Construit directement les objets
	 */
	public WriteXML(Output o) {
		try{
			JAXBContext contexte = JAXBContext.newInstance(Output.class);
			Marshaller m = contexte.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(o, System.out);
			
			Writer w = null;
			try {
				try {
					w = new FileWriter(xmlFileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.marshal(o, w);
			} finally {
				try {
					w.close();
				} catch (Exception e) {
				}
			}
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
