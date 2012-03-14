//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.14 at 05:16:39 PM CET 
//


package entityOutput;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indicator element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="indicator">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="alpha" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="beta" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="ir" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="perf" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="period" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="te" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="vol" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "indicator")
public class Indicator {

    @XmlAttribute(required = true)
    protected double alpha;
    @XmlAttribute(required = true)
    protected double beta;
    @XmlAttribute(required = true)
    protected double ir;
    @XmlAttribute(required = true)
    protected double perf;
    @XmlAttribute(required = true)
    protected String period;
    @XmlAttribute(required = true)
    protected double te;
    @XmlAttribute(required = true)
    protected double vol;

    /**
     * Gets the value of the alpha property.
     * 
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    public void setAlpha(double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    public void setBeta(double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the ir property.
     * 
     */
    public double getIr() {
        return ir;
    }

    /**
     * Sets the value of the ir property.
     * 
     */
    public void setIr(double value) {
        this.ir = value;
    }

    /**
     * Gets the value of the perf property.
     * 
     */
    public double getPerf() {
        return perf;
    }

    /**
     * Sets the value of the perf property.
     * 
     */
    public void setPerf(double value) {
        this.perf = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriod(String value) {
        this.period = value;
    }

    /**
     * Gets the value of the te property.
     * 
     */
    public double getTe() {
        return te;
    }

    /**
     * Sets the value of the te property.
     * 
     */
    public void setTe(double value) {
        this.te = value;
    }

    /**
     * Gets the value of the vol property.
     * 
     */
    public double getVol() {
        return vol;
    }

    /**
     * Sets the value of the vol property.
     * 
     */
    public void setVol(double value) {
        this.vol = value;
    }

}
