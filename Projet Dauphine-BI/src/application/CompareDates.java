package application;

import java.util.Comparator;

import javax.xml.datatype.XMLGregorianCalendar;

public class CompareDates implements Comparator {

	int retval;

	// 
	public int compare(Object o1, Object o2) {
		
		if(((XMLGregorianCalendar) o1).toGregorianCalendar().compareTo( ((XMLGregorianCalendar) o2).toGregorianCalendar()) > 0){
			return -1;
		}
		else if(((XMLGregorianCalendar) o1).toGregorianCalendar().compareTo( ((XMLGregorianCalendar) o2).toGregorianCalendar()) == 0){
			return 0;
		}
		else{
			return 1;
		}
		
	}

	
	
}
