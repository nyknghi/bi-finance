package application;

import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

public class ResultatIndicators {

	TreeMap<XMLGregorianCalendar, Double> valuesActionAdjusted;
	TreeMap<XMLGregorianCalendar, Double> valuesBenchmarkAdjusted;
	
	// Indicateurs
	private double performance;
	private double volatilite;
	private double param;
	private String period;
	private double beta;
	private double alpha;


	public ResultatIndicators(TreeMap<XMLGregorianCalendar, Double> valuesActionAdjusted, TreeMap<XMLGregorianCalendar, Double> valuesBenchmarkAdjusted, double p){
		this.param = p;
		this.valuesActionAdjusted = valuesActionAdjusted;
		this.valuesBenchmarkAdjusted = valuesBenchmarkAdjusted;
		calcul();
	}
	
	public TreeMap<XMLGregorianCalendar, Double> getValuesActionAdjusted() {
		return valuesActionAdjusted;
	}

	public void setValuesActionAdjusted(
			TreeMap<XMLGregorianCalendar, Double> valuesActionAdjusted) {
		this.valuesActionAdjusted = valuesActionAdjusted;
	}

	public TreeMap<XMLGregorianCalendar, Double> getValuesBenchmarkAdjusted() {
		return valuesBenchmarkAdjusted;
	}

	public void setValuesBenchmarkAdjusted(
			TreeMap<XMLGregorianCalendar, Double> valuesBenchmarkAdjusted) {
		this.valuesBenchmarkAdjusted = valuesBenchmarkAdjusted;
	}

	public double getPerformance() {
		return performance;
	}

	public void setPerformance(double performance) {
		this.performance = performance;
	}

	public double getVolatilite() {
		return volatilite;
	}

	public void setVolatilite(double volatilite) {
		this.volatilite = volatilite;
	}

	public double getParam() {
		return param;
	}

	public void setParam(double param) {
		this.param = param;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	private void calcul(){
		this.performance = Calculation.indicatorPerfA(valuesActionAdjusted, param);
		this.volatilite = Calculation.indicatorVol(valuesActionAdjusted, param);
		this.beta = Calculation.indicatorBeta(valuesActionAdjusted, valuesBenchmarkAdjusted, param);
		this.alpha = Calculation.indicatorAlpha(valuesActionAdjusted, valuesBenchmarkAdjusted, param);
		this.period = (int)param +"M";
	}
}
