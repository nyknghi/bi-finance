//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.14 at 03:35:40 PM CET 
//


package entityOutput;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stock element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="stock">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{}prices"/>
 *           &lt;element ref="{}indicators"/>
 *           &lt;element ref="{}trackingError"/>
 *           &lt;element ref="{}informationRatio"/>
 *         &lt;/sequence>
 *         &lt;attribute name="benchId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="benchmark" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="country" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="industry" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="sector" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="zone" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pricesAndIndicatorsAndTrackingError"
})
@XmlRootElement(name = "stock")
public class Stock {

    @XmlElements({
        @XmlElement(name = "trackingError", required = true, type = TrackingError.class),
        @XmlElement(name = "indicators", required = true, type = Indicators.class),
        @XmlElement(name = "informationRatio", required = true, type = InformationRatio.class),
        @XmlElement(name = "prices", required = true, type = Prices.class)
    })
    protected List<Object> pricesAndIndicatorsAndTrackingError;
    @XmlAttribute(required = true)
    protected String benchId;
    @XmlAttribute(required = true)
    protected String benchmark;
    @XmlAttribute(required = true)
    protected String country;
    @XmlAttribute(required = true)
    protected String id;
    @XmlAttribute(required = true)
    protected String industry;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected String sector;
    @XmlAttribute(required = true)
    protected String zone;

    /**
     * Gets the value of the pricesAndIndicatorsAndTrackingError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pricesAndIndicatorsAndTrackingError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPricesAndIndicatorsAndTrackingError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrackingError }
     * {@link Indicators }
     * {@link InformationRatio }
     * {@link Prices }
     * 
     * 
     */
    public List<Object> getPricesAndIndicatorsAndTrackingError() {
        if (pricesAndIndicatorsAndTrackingError == null) {
            pricesAndIndicatorsAndTrackingError = new ArrayList<Object>();
        }
        return this.pricesAndIndicatorsAndTrackingError;
    }

    /**
     * Gets the value of the benchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenchId() {
        return benchId;
    }

    /**
     * Sets the value of the benchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenchId(String value) {
        this.benchId = value;
    }

    /**
     * Gets the value of the benchmark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenchmark() {
        return benchmark;
    }

    /**
     * Sets the value of the benchmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenchmark(String value) {
        this.benchmark = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the industry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * Sets the value of the industry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndustry(String value) {
        this.industry = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSector(String value) {
        this.sector = value;
    }

    /**
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
    }

}
