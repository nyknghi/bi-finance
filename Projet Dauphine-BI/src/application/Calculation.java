package application;

import java.util.ArrayList;
import java.util.Calendar;
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
		
	public static double IndicatorPerfA(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		return Math.pow(datas.get(datas.size()-1)/datas.get(datas.size()-1-param*4),365.0/param*4.0*7.0)-1;
	}
	
	public static double Moyenne(TreeMap<XMLGregorianCalendar, Double> datas, double param){
		double res = 0.0;
		
		for(int i = datas.size()-1; i < datas.size()-1-param*4;i--){
			res += datas.get(i);
		}		
		return res/param*4;
	}
	
	public static double IndicatorVol(TreeMap<XMLGregorianCalendar, Double> datas, int param){
		double res = 0.0;
		for(int i = datas.size()-1; i < datas.size()-1-param*4;i--){
			res += Math.pow(Math.log(datas.get(i)/datas.get(i-1))-Moyenne(datas, param), 2);
		}
		return Math.sqrt(res/(param*4-1)*52.0);
	}
	
	public static double IndicatorTE(TreeMap<XMLGregorianCalendar, Double> datas, int param){
		double res = 0.0;
		
		// On calcule la moyenne de la performance relative
		
		
		return res;
	}
}
