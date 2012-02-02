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

	//public static void compareDates()
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
	
	
	public static TreeMap<XMLGregorianCalendar, Double> makeEvolution( HashMap<XMLGregorianCalendar, Double> values){
		
		CompareDates cDates = new CompareDates();
		TreeMap<XMLGregorianCalendar, Double> valuesNew = new TreeMap<XMLGregorianCalendar, Double>(cDates);
		//double FirstValueTmp = values.;
		double LastValueTmp;
		
		for(XMLGregorianCalendar step : values.keySet()){
			LastValueTmp = values.get(step);
			valuesNew.put(step, values.get(step));
		}
		
		return null;
	}
}
