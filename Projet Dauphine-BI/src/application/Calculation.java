package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class Calculation {

	//public static void compareDates()
	public static ArrayList fixSteps(XMLGregorianCalendar startdate, XMLGregorianCalendar enddate, int step){
		ArrayList stepDates = new ArrayList(); // Liste des étapes 
		stepDates.add(startdate);
		XMLGregorianCalendar date = startdate;
		try {
			Duration duration = DatatypeFactory.newInstance().newDuration(step*24*3600*1000);
			
			while(enddate.toGregorianCalendar().compareTo(date.toGregorianCalendar())> 0){
				date.add(duration);
				stepDates.add(date);
			}
			return stepDates;
			
			
			
			
			
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
