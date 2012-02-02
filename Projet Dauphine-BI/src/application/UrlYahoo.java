package application;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import entity.Stock;


public class UrlYahoo {

	String urlAction = "http://ichart.yahoo.com/table.csv?s=";
	String urlBenchmark = "http://ichart.yahoo.com/table.csv?s=";
	XMLGregorianCalendar enddate;
	
	
	public UrlYahoo(){
		
	}
	
	public UrlYahoo(Stock stock, XMLGregorianCalendar startdate){
		
		// Déclaration de la date d'aujourd'hui comme date de fin
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dat;
		dat = dateFormat.format(date);
		try {
			enddate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dat);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		urlAction = "http://ichart.yahoo.com/table.csv?s=";
		urlAction += stock.getId()
			+"&a="+String.valueOf(startdate.getMonth()-1)
			+"&b="+String.valueOf(startdate.getDay())
			+"&c="+String.valueOf(startdate.getYear())
			+"&d="+String.valueOf(enddate.getMonth()-1)
			+"&e="+String.valueOf(enddate.getDay())
			+"&f="+String.valueOf(enddate.getYear())
			+"&g=d" // pour préciser que c'est hebdomadaire
			+"&ignore=.csv";
		
		urlBenchmark="http://ichart.yahoo.com/table.csv?s=";
		urlBenchmark += stock.getBenchId()
		+"&a="+String.valueOf(startdate.getMonth()-1)
		+"&b="+String.valueOf(startdate.getDay())
		+"&c="+String.valueOf(startdate.getYear())
		+"&d="+String.valueOf(enddate.getMonth()-1)
		+"&e="+String.valueOf(enddate.getDay())
		+"&f="+String.valueOf(enddate.getYear())
		+"&g=d" // pour préciser que c'est hebdomadaire
		+"&ignore=.csv";
	}

	public XMLGregorianCalendar getEnddate() {
		return enddate;
	}

	public void setEnddate(XMLGregorianCalendar enddate) {
		this.enddate = enddate;
	}

	public String getUrlAction() {
		return urlAction;
	}

	public void setUrlAction(String urlAction) {
		this.urlAction = urlAction;
	}

	public String getUrlBenchmark() {
		return urlBenchmark;
	}

	public void setUrlBenchmark(String urlBenchmark) {
		this.urlBenchmark = urlBenchmark;
	}
}
