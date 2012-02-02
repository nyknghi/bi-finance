package application;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import entity.Stock;


public class Main {

	private static final String FILE_NAME = "dataSource/bdd.csv";
	public static void main (String[] args){
		
		// On lit et on charge les données du fichier input.xml
		ReadXml readxml = new ReadXml();
		
		
		// Pour chaque stock
		int i = 1;
		for (Stock stock : readxml.getInput().getStock())
		{
			// On crée deux fichiers csv, un pour l'action, et un pour le benchmark
			File fileAction = new File("Action"+String.valueOf(i)+".csv");
			File fileBenchmark = new File("Benchmark"+String.valueOf(i)+".csv");
			
			// On récupère les adresses correspondantes sur le site yahoo.finance dans notre objet urlyahoo
			UrlYahoo urlyahoo = new UrlYahoo(stock, readxml.getInput().getStartdate());
			
			// On télécharge les fichiers
			UrlHelper u = null;
			u.downloadFile(urlyahoo.getUrlAction(), fileAction);
			u.downloadFile(urlyahoo.getUrlBenchmark(), fileBenchmark);			
			
			// On affine les fichiers ainsi obtenus (en gardant la première et la dernière colonne), et on les stocke en mémoire
			CsvFile fileActionCsv = new CsvFile(fileAction);
			CsvFile fileBenchmarkCsv = new CsvFile(fileBenchmark);
			Set cles = fileActionCsv.getHashData().keySet();
			Iterator it = cles.iterator();
			while(it.hasNext()){
				XMLGregorianCalendar cle = (XMLGregorianCalendar) it.next();
				double valeur = fileActionCsv.getHashData().get(cle);
				System.out.println(cle.toString() + " "+ valeur);
				break;
			}
			
			/*for(int j = 0; j < fileActionCsv.getNewData().size();j++){
				System.out.println(fileActionCsv.getNewData().get(j)[0]+" "+fileActionCsv.getNewData().get(j)[1]);				
			}*/
			i++;
			
			/*Date date = new Date(); 
			XMLGregorianCalendar xmlCalendar;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String dat = dateFormat.format(date);
			try {
				xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(dat);
				System.out.println(xmlCalendar);
				
				
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			break;
			
		}
		
		
		
		
	}
}
