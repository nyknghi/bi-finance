package application;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

//import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

//import entity.Stock;

import entityOutput.Indicator;
import entityOutput.Indicators;
import entityOutput.Obs;
import entityOutput.Output;
import entityOutput.Prices;


public class Main {

	@SuppressWarnings("unchecked")
	public static void main (String[] args){
		
		// On lit et on charge les données du fichier input.xml
		ReadXml readxml = new ReadXml();
		Output output = new Output();
		
		final XMLGregorianCalendar startdate = (XMLGregorianCalendar) readxml.getInput().getStartdate().clone();
		// Pour chaque stock
		int i = 1;
		for (entity.Stock stock : readxml.getInput().getStock())
		{
			// On crée deux fichiers csv, un pour l'action, et un pour le benchmark
			File fileAction = new File("dataSource/Action"+String.valueOf(i)+".csv");
			File fileBenchmark = new File("dataSource/Benchmark"+String.valueOf(i)+".csv");
			
			// On récupère les adresses correspondantes sur le site yahoo.finance dans notre objet urlyahoo
			UrlYahoo urlyahoo = new UrlYahoo(stock, startdate);
			System.out.println(startdate);
			
			// On télécharge les fichiers
			UrlHelper.downloadFile(urlyahoo.getUrlAction(), fileAction);
			UrlHelper.downloadFile(urlyahoo.getUrlBenchmark(), fileBenchmark);		
			
			// On affine les fichiers ainsi obtenus (en gardant la première et la dernière colonne), et on les stocke en mémoire
			CsvFile fileActionCsv = new CsvFile(fileAction);
			CsvFile fileBenchmarkCsv = new CsvFile(fileBenchmark);
			HashMap<XMLGregorianCalendar, Double> datasAction = fileActionCsv.getHashData();
			HashMap<XMLGregorianCalendar, Double> datasBenchmark = fileBenchmarkCsv.getHashData();
			
			// On récupère la liste des étapes
			ArrayList<XMLGregorianCalendar> stepsDates = new ArrayList<XMLGregorianCalendar>();
			//System.out.println(readxml.getInput().getStartdate());
			stepsDates = Calculation.fixSteps(readxml.getInput().getStartdate(), urlyahoo.getEnddate(), 7);
			
			CompareDates cDates = new CompareDates(); // Comparateur de dates
			// On récupère les données pour l'action
			TreeMap<XMLGregorianCalendar, Double> valuesAction = new TreeMap<XMLGregorianCalendar, Double>(cDates);
			valuesAction = Calculation.findStepsValues(stepsDates, datasAction);
			
			// puis pour le Benchmark
			TreeMap<XMLGregorianCalendar, Double> valuesBenchmark = new TreeMap<XMLGregorianCalendar, Double>(cDates);
			valuesBenchmark = Calculation.findStepsValues(stepsDates, datasBenchmark);
			
			// On a donc les données hebdomadaires pour l'action et le benchmark
			// Maintenant, on souhaite les transformer en gardant leur évolution depuis une base de 100
			
			TreeMap<XMLGregorianCalendar, Double> valuesActionAdjusted = new TreeMap<XMLGregorianCalendar, Double>(cDates);
			TreeMap<XMLGregorianCalendar, Double> valuesBenchmarkAdjusted = new TreeMap<XMLGregorianCalendar, Double>(cDates);
			valuesActionAdjusted = Calculation.changeValues(valuesAction);
			valuesBenchmarkAdjusted = Calculation.changeValues(valuesBenchmark);
			
			// Définition des périodes
			ArrayList<Double> params = new ArrayList<Double>();
			params.add(3.0);
			params.add(6.0);
			params.add(12.0);
			
			// Pour chaque période, calcul des indicateurs
			ArrayList<ResultatIndicators> results = new ArrayList<ResultatIndicators>();
			for(double param : params){
				results.add(new ResultatIndicators(valuesActionAdjusted, valuesBenchmarkAdjusted, param));
				//System.out.println("Performance : " + results.get(results.size()-1).getPerformance());
				//System.out.println("Volatilite : " + results.get(results.size()-1).getVolatilite());
				//System.out.println("Information Ratio : " + results.get(results.size()-1).getInformationRatio());
				//System.out.println("Beta : " + results.get(results.size()-1).getBeta());
				//System.out.println("Alpha : " + results.get(results.size()-1).getAlpha());
			}
			
			entityOutput.Stock stockOut = new entityOutput.Stock();
			Indicators indicators = new Indicators();
			stockOut.setIndicators(indicators);
			
			stockOut.setBenchId(stock.getBenchId());
			stockOut.setBenchmark(stock.getBenchmark());
			stockOut.setCountry(stock.getCountry());
			stockOut.setId(stock.getId());
			stockOut.setIndustry(stock.getIndustry());
			stockOut.setName(stock.getName());
			stockOut.setSector(stock.getSector());
			stockOut.setZone(stock.getZone());
			
			for(int j=0; j < params.size();j++){
				Indicator indicator = new Indicator();
				
				indicator.setAlpha(results.get(j).getAlpha());
				indicator.setBeta(results.get(j).getBeta());
				indicator.setIr(results.get(j).getInformationRatio());
				indicator.setPerf(results.get(j).getPerformance());
				indicator.setPeriod(results.get(j).getPeriod());
				indicator.setVol(results.get(j).getVolatilite());
				
				stockOut.getIndicators().getIndicator().add(indicator);
			}
			
			double te = Calculation.indicatorTE(valuesActionAdjusted, valuesBenchmarkAdjusted, 12.0);
			indicators.setTe(te);
			//System.out.println("Tracking Error : " + te);
			
			Prices prices = new Prices();
			stockOut.setPrices(prices);
			for(XMLGregorianCalendar date : valuesActionAdjusted.keySet()){
				Obs obs = new Obs();
				obs.setDate(date);
				obs.setPrice(valuesActionAdjusted.get(date));
				obs.setPriceBench(valuesBenchmarkAdjusted.get(date));
				stockOut.getPrices().getObs().add(obs);
			}
			output.getStock().add(stockOut);
			i++;
			
		}
		
		// create JAXB context and instantiate marshaller		
		WriteXML writexml = new WriteXML(output);

		
		
		
		
	}
}
