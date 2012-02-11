package application;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import entity.Stock;


public class Main {

	public static void main (String[] args){
		
		// On lit et on charge les données du fichier input.xml
		ReadXml readxml = new ReadXml();
		
		
		// Pour chaque stock
		int i = 1;
		for (Stock stock : readxml.getInput().getStock())
		{
			// On crée deux fichiers csv, un pour l'action, et un pour le benchmark
			File fileAction = new File("dataSource/Action"+String.valueOf(i)+".csv");
			File fileBenchmark = new File("dataSource/Benchmark"+String.valueOf(i)+".csv");
			
			// On récupère les adresses correspondantes sur le site yahoo.finance dans notre objet urlyahoo
			UrlYahoo urlyahoo = new UrlYahoo(stock, readxml.getInput().getStartdate());			
			
			// On télécharge les fichiers
			UrlHelper u = null;
			u.downloadFile(urlyahoo.getUrlAction(), fileAction);
			u.downloadFile(urlyahoo.getUrlBenchmark(), fileBenchmark);		
			
			// On affine les fichiers ainsi obtenus (en gardant la première et la dernière colonne), et on les stocke en mémoire
			CsvFile fileActionCsv = new CsvFile(fileAction);
			CsvFile fileBenchmarkCsv = new CsvFile(fileBenchmark);
			HashMap<XMLGregorianCalendar, Double> datasAction = fileActionCsv.getHashData();
			HashMap<XMLGregorianCalendar, Double> datasBenchmark = fileBenchmarkCsv.getHashData();
			
			// On récupère la liste des étapes
			ArrayList<XMLGregorianCalendar> stepsDates = new ArrayList<XMLGregorianCalendar>();
			stepsDates = Calculation.fixSteps(readxml.getInput().getStartdate(), urlyahoo.getEnddate(), 7);
			
			// On récupère les données pour l'action
			TreeMap<XMLGregorianCalendar, Double> valuesAction = new TreeMap<XMLGregorianCalendar, Double>();
			valuesAction = Calculation.findStepsValues(stepsDates, datasAction, 7);
			
			// puis pour le Benchmark
			TreeMap<XMLGregorianCalendar, Double> valuesBenchmark = new TreeMap<XMLGregorianCalendar, Double>();
			valuesBenchmark = Calculation.findStepsValues(stepsDates, datasBenchmark, 7);
			
			// On a donc les données hebdomadaires pour l'action et le benchmark
			// Maintenant, on souhaite les transformer en gardant leur évolution depuis une base de 100
			
			
			i++;
			break;
			
		}
		
		
		
		
	}
}
