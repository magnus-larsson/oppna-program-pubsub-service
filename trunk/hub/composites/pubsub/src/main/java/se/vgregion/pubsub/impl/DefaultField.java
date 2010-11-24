package se.vgregion.pubsub.impl;

import java.io.StringReader;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import se.vgregion.dao.domain.patterns.entity.AbstractEntity;
import se.vgregion.pubsub.Field;

@Entity
@Table(name="FIELDS")
public class DefaultField extends AbstractEntity<Long> implements Field {

    private static final Builder PARSER = new Builder();;

    @Id
    @GeneratedValue
    private Long id;
    
    @Basic(optional=false)
    private String xml;

    
    private static Element createElement(String namespace, String name, String value) {
        Element elm = new Element(name, namespace);
        elm.appendChild(value);
        return elm;
    }

    // For JPA
    protected DefaultField() {
    }

    
    public DefaultField(String namespace, String name, String value) {
        this(createElement(namespace, name, value));
    }

    public DefaultField(Element elm) {
        // TODO ugly hack to retain namespaces
        this.xml = new Document((Element) elm.copy()).toXML().replaceFirst("<.+>", "");
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Element toXml() {
        try {
            Document doc = PARSER.build(new StringReader(xml));
            Element elm = doc.getRootElement();
            return (Element) elm.copy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

}
