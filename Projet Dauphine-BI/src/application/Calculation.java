package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class Calculation {

	
	public static ArrayList<XMLGregorianCalendar> fixSteps(XMLGregorianCalendar startdate, XMLGregorianCalendar enddate, int step){
		ArrayList<XMLGregorianCalendar> stepDates = new ArrayList<XMLGregorianCalendar>(); // Liste des étapes 
		stepDates.add(startdate);
		XMLGregorianCalendar date = (XMLGregorianCalendar) startdate.clone();
		try {
			Duration duration = DatatypeFactory.newInstance().newDuration(step*24*3600*1000);
			
			while(enddate.toGregorianCalendar().compareTo(date.toGregorianCalendar())> 0){
				XMLGregorianCalendar date2 = (XMLGregorianCalendar) stepDates.get(stepDates.size()-1).clone();
				date2.add(duration);
				stepDates.add(date2);
				date = (XMLGregorianCalendar) date2.clone();
			}
			stepDates.remove(stepDates.size()-1);
			return stepDates;		
			
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public static TreeMap<XMLGregorianCalendar, Double> findStepsValues(ArrayList<XMLGregorianCalendar> stepsDates, HashMap<XMLGregorianCalendar, Double> datas, int step){
		
		CompareDates cDates = new CompareDates();
		TreeMap<XMLGregorianCalendar, Double> values = new TreeMap<XMLGregorianCalendar, Double>(cDates);
		
		for(XMLGregorianCalendar stepDates : stepsDates){
			if( datas.get(stepDates) != null){
				values.put(stepDates, datas.get(stepDates));
			}
			else{
				boolean vFound = false;
				int cpt = -1;
				while(vFound == false && cpt > -10){
					Duration duration;
					try {
						duration = DatatypeFactory.newInstance().newDuration(-24*3600*1000);
						stepDates.add(duration); // On retire un jour à la date actuelle
						if( datas.get(stepDates) != null){ // On vérifie si elle est dans la HashMap
							double tmpvalue = datas.get(stepDates);
							duration = DatatypeFactory.newInstance().newDuration(-cpt*24*3600*1000); // On retourne à la date initiale
							stepDates.add(duration);
							values.put(stepDates, tmpvalue); // Si oui, on entre la valeur correspondante
							vFound = true;
						}
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();
					}
					cpt--; // On boucle jusqu'à 10 fois
				}
			}
			
		}
		return values;
	}
	
	
	@SuppressWarnings("unchecked")
	public static TreeMap<XMLGregorianCalendar, Double> changeValues (TreeMap<XMLGregorianCalendar, Double> datas ){
		
		CompareDates cDates = new CompareDates();
		TreeMap<XMLGregorianCalendar, Double> datasAdjusted = new TreeMap<XMLGregorianCalendar, Double>(cDates);
		int cpt = 0;
		double lastValue = 0.0;
		
		for(XMLGregorianCalendar date : datas.keySet()){
			if(cpt < 1){
				datasAdjusted.put(date, 100.0);
				lastValue = datas.get(date);
				cpt++;
			}
			else{
				datasAdjusted.put(date, 100.0*datas.get(date)/lastValue);
				lastValue = datas.get(date);
			}
			
		}
		
		return datasAdjusted;
	}
	
	// Calcule la performance classique
	public static double indicatorPerf(double v0, double v1){
		return v1/v0-1;
	}
		
	// Calcule l'indicateur de performance d'un fonds sur une période donnée
	public static double indicatorPerfA(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		XMLGregorianCalendar startDate = (XMLGregorianCalendar) datas.lastKey().clone();
		try {
			Duration duration = DatatypeFactory.newInstance().newDuration((long) (-24*3600*1000*7));
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) startDate.clone();
			for(int i=0; i < (int) param*4;i++){
				endDate.add(duration);
			}
			if(!datas.containsKey(endDate)){
				System.out.println("Problème de date !");
			}
			return Math.pow(datas.get(startDate)/datas.get(endDate),365.0/(param*4.0*7.0)) - 1;
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return 0.0;
		}      
	}
	
	// Calcule la moyenne d'un fonds sur une période donnée
	public static double Moyenne(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		double res = 0.0;
		
		XMLGregorianCalendar startDate = (XMLGregorianCalendar) datas.lastKey().clone();
		Duration duration;
		try {
			duration = DatatypeFactory.newInstance().newDuration((long) (-24*3600*1000*7));
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) startDate.clone();
			for(int i = 0; i < param*4;i++){
				res += datas.get(endDate);
				endDate.add(duration);
			}		
			return res/(param*4);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	public static double MoyennePerfH(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		double res = 0.0;
		
		XMLGregorianCalendar startDate = (XMLGregorianCalendar) datas.lastKey().clone();
		Duration duration;
		try {
			duration = DatatypeFactory.newInstance().newDuration((long) (-24*3600*1000*7));
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) startDate.clone();
			for(int i=0;i<param*4;i++){
				endDate.add(duration);
				res += Math.log(datas.get(startDate)/datas.get(endDate));
				startDate.add(duration);
			}
			return res/(param*4);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	
	// Calcule l'indicateur de volatilité d'un fonds sur une période donnée
	public static double indicatorVol(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		double res = 0.0;
		
		XMLGregorianCalendar startDate = (XMLGregorianCalendar) datas.lastKey().clone();
		Duration duration;
		try {
			duration = DatatypeFactory.newInstance().newDuration((long) (-24*3600*1000*7));
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) startDate.clone();
			for(int i = 0; i < param*4;i++){
				endDate.add(duration);
				res += Math.pow(Math.log(datas.get(startDate)/datas.get(endDate))-MoyennePerfH(datas, param), 2);
				startDate.add(duration);
			}
			return Math.sqrt(52*res/(param*4-1));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	// Calcule l'indicateur de tracking error sur une période donnée
	public static double indicatorTE(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		
		try {
			checkDates(datas, datasB);// On vérifie que les TreeMap correspondent
			
			XMLGregorianCalendar startDate = (XMLGregorianCalendar) datas.lastKey().clone();
			Duration duration;
			try {
				duration = DatatypeFactory.newInstance().newDuration((long) (-24*3600*1000*7));
				XMLGregorianCalendar endDate = (XMLGregorianCalendar) startDate.clone();
				// On calcule la moyenne de la performance relative
				double res = 0.0;
				double perfMA = 0.0, perfMB = 0.0, perfM = 0.0;
				
				for(int i = 0; i < param*4;i++){
					endDate.add(duration);
					perfMA += indicatorPerf(datas.get(endDate), datas.get(startDate));
					perfMB += indicatorPerf(datasB.get(endDate), datasB.get(startDate));
					//System.out.println("perfMA = " + perfMA + " perfMB = " + perfMB);
					startDate.add(duration);
				}
				perfM = (perfMA -perfMB)/(param*4);
				//System.out.println("PerfM = "+perfM);
				
				// On effectue ensuite la différence de chaque performance de l'indice par rapport à la moyenne
				startDate = (XMLGregorianCalendar) datas.lastKey().clone(); // Réinitialisation
				endDate = (XMLGregorianCalendar) startDate.clone();
				for(int i = 0; i < param*4;i++){
					endDate.add(duration);
					double perfAi = indicatorPerf(datas.get(endDate), datas.get(startDate)) - indicatorPerf(datasB.get(endDate), datasB.get(startDate)); // Performance relative actuelle
					res += Math.pow(perfAi - perfM,2);
					startDate.add(duration);
					//System.out.println("perfAi = "+perfAi);
				}
				return Math.sqrt(52*res/(param*4-1));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			return 0.0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static void checkDates(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB) throws Exception {
	
		if(datas.size() != datasB.size()){
			throw new Exception("Les deux TreeMap ne correspondent pas !");
		}
		for(XMLGregorianCalendar date : datas.keySet()){
			if(!datasB.containsKey(date)){
				throw new Exception("Les deux TreeMap ne correspondent pas !");
			}
		}
	}
	
	public static double indicatorRatioInformation(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		return (indicatorPerfA(datas, param) - indicatorPerfA(datasB, param))/indicatorTE(datas, datasB, param);
	}
	
	public static double covariance(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		double moyA = Moyenne(datas, param);
		double moyB = Moyenne(datasB, param);
		double moyAB = 0.0;
		for(int i = datas.size()-1; i < datas.size()-1-param*4;i--){
			moyAB += datas.get(i)*datasB.get(i);
		}
		moyAB = moyAB/(param*4);
		
		return moyAB - moyA*moyB;
	}
	
	public static double indicatorBeta(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		try {
			checkDates(datas, datasB);
			
			double cov = covariance(datas, datasB, param); // Calcul de la covariance
			double var = covariance(datasB, datasB, param); // Calcul de la variance
			return cov/var;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static double indicatorAlpha(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		return indicatorPerfA(datas, param)-indicatorBeta(datas, datasB, param)*indicatorPerfA(datasB, param);	
	}
}
