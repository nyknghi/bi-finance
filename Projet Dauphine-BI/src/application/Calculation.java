package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;
import java.lang.*;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cpt--; // On boucle jusqu'à 10 fois
				}
			}
			
		}
		return values;
	}
	
	
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
	
	// Calcule la performance hebdomadaire d'un fonds 
	public static double IndicatorPerfA(double v0, double v1){
		return Math.pow(v1/v0,365.0/7.0)-1;
	}
		
	// Calcule l'indicateur de performance d'un fonds sur une période donnée
	  public static double IndicatorPerfA(TreeMap<XMLGregorianCalendar, Double> datas, double param){
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
                  return Math.pow(datas.get(startDate)/datas.get(endDate),365.0/param*4.0*7.0)-1;
          } catch (DatatypeConfigurationException e) {
                  // TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
	}
	
	
	// Calcule l'indicateur de volatilité d'un fonds sur une période donnée
	public static double IndicatorVol(TreeMap<XMLGregorianCalendar, Double> datas, double param){
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
			return Math.sqrt(res/(param*4-1)*52.0);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}
	
	// Calcule l'indicateur de tracking error sur une période donnée
	public static double IndicatorTE(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		
		try {
			CheckDates(datas, datasB);// On vérifie que les TreeMap correspondent
			
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
					perfMA += IndicatorPerfA(datas.get(endDate), datas.get(startDate));
					perfMB += IndicatorPerfA(datasB.get(endDate), datasB.get(startDate));
					startDate.add(duration);
				}
				perfM = (perfMB -perfMA)/(param*4);
				System.out.println("PerfM = "+perfM);
				// On effectue ensuite la différence de chaque performance de l'indice par rapport à la moyenne
				startDate = (XMLGregorianCalendar) datas.lastKey().clone(); // Réinitialisation
				endDate = (XMLGregorianCalendar) startDate.clone();
				for(int i = 0; i < param*4;i++){
					endDate.add(duration);
					double perfAi = IndicatorPerfA(datas.get(endDate), datas.get(startDate)) - IndicatorPerfA(datasB.get(endDate), datasB.get(startDate)); // Performance relative actuelle
					res += Math.pow(perfAi - perfM,2);
					startDate.add(duration);
					System.out.println("perfAi = "+perfAi);
				}
				return Math.sqrt(res/(param*4)*52);
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0.0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static void CheckDates(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB) throws Exception {
	
		if(datas.size() != datasB.size()){
			throw new Exception("Les deux TreeMap ne correspondent pas !");
		}
		for(XMLGregorianCalendar date : datas.keySet()){
			if(!datasB.containsKey(date)){
				throw new Exception("Les deux TreeMap ne correspondent pas !");
			}
		}
	}
	
	public static double IndicatorRatioInformation(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		return (IndicatorPerfA(datas, param) - IndicatorPerfA(datasB, param))/IndicatorTE(datas, datasB, param);
	}
	
	public static double Covariance(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		double moyA = Moyenne(datas, param);
		double moyB = Moyenne(datasB, param);
		double moyAB = 0.0;
		for(int i = datas.size()-1; i < datas.size()-1-param*4;i--){
			moyAB += datas.get(i)*datasB.get(i);
		}
		moyAB = moyAB/(param*4);
		
		return moyAB - moyA*moyB;
	}
	
	public static double IndicatorBeta(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		try {
			CheckDates(datas, datasB);
			
			double cov = Covariance(datas, datasB, param); // Calcul de la covariance
			double var = Covariance(datasB, datasB, param); // Calcul de la variance
			return cov/var;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static double IndicatorAlpha(TreeMap<XMLGregorianCalendar, Double> datas, TreeMap<XMLGregorianCalendar, Double> datasB, double param){
		return IndicatorPerfA(datas, param)-IndicatorBeta(datas, datasB, param)*IndicatorPerfA(datasB, param);	
	}
}
