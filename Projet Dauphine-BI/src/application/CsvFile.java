package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class CsvFile {

	public final static char SEPARATOR = ',';
	
	private File file;
	private List<String> lines ; // Lignes concaténées
	private List<String[]> data; // Lignes séparées
	private List<String[]> newData; // Lignes affinées
	private HashMap<XMLGregorianCalendar, Double> hashData = new HashMap<XMLGregorianCalendar, Double>(); 
	
	public HashMap<XMLGregorianCalendar, Double> getHashData() {
		return hashData;
	}

	public void setHashData(HashMap<XMLGregorianCalendar, Double> hashData) {
		this.hashData = hashData;
	}

	public CsvFile(File file){
		this.file = file;
		
		init();
		refine();
	}
	
	private void init () {
		this.lines = CsvFileHelper.readFile(file);
		
		this.data= new ArrayList<String[]>(this.lines.size());
		String sep = new Character(SEPARATOR).toString();
		for(String line : this.lines) {
			String[] oneData = line.split(sep);
			this.data.add(oneData);
		}
	}
	
	private void refine (){
		this.data.remove(0);
		
		this.newData = new ArrayList<String[]>(this.data.size()-1);
		for(String[] line : this.data){
			try {
				hashData.put(DatatypeFactory.newInstance().newXMLGregorianCalendar(line[0]), Double.parseDouble(line[line.length-1]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			String[] newLine = new String[2];
			newLine[0] = line[0];
			newLine[1] = line[line.length-1];
			this.newData.add(newLine);
		}
		
		
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<String> getLines() {
		return this.lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public List<String[]> getData() {
		return this.data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

	public List<String[]> getNewData() {
		return this.newData;
	}

	public void setNewData(List<String[]> newData) {
		this.newData = newData;
	}

}
