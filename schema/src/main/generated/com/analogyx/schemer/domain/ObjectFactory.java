//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.23 at 08:01:52 AM PDT 
//


package com.analogyx.schemer.domain;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.analogyx.schemer.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.analogyx.schemer.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Database }
     * 
     */
    public Database createDatabase() {
        return new Database();
    }

    /**
     * Create an instance of {@link Tabletype }
     * 
     */
    public Tabletype createTabletype() {
        return new Tabletype();
    }

    /**
     * Create an instance of {@link Indextype }
     * 
     */
    public Indextype createIndextype() {
        return new Indextype();
    }

    /**
     * Create an instance of {@link Rowtype }
     * 
     */
    public Rowtype createRowtype() {
        return new Rowtype();
    }

    /**
     * Create an instance of {@link Rowdatatype }
     * 
     */
    public Rowdatatype createRowdatatype() {
        return new Rowdatatype();
    }

    /**
     * Create an instance of {@link Columntype }
     * 
     */
    public Columntype createColumntype() {
        return new Columntype();
    }

}
