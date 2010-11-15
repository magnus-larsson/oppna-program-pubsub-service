package se.vgregion.push.types;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import se.vgregion.push.types.Entry.EntryBuilder;
import se.vgregion.push.types.Feed.FeedBuilder;

public class Rss2Parser extends AbstractParser {

    public Feed parse(URI url, Document document) {
        Element rss = document.getRootElement();
        FeedBuilder builder = new FeedBuilder(url, ContentType.RSS);
        
        Element channel = rss.getFirstChildElement("channel");
        
        if(channel == null) {
            throw new IllegalArgumentException("Invalid RSS, missing channel element");
        }
        
        Elements children = channel.getChildElements();
        
        for(int i = 0; i<children.size(); i++) {
            Element child = children.get(i);
            if("link".equals(child.getLocalName())) {
                builder.id(child.getValue());
            } else if("pubDate".equals(child.getLocalName())) {
                builder.updated(parseDateTime(child.getValue()));
            } else if("item".equals(child.getLocalName())) {
                builder.entry(parseItem(child));
            } else {
                builder.custom(child);
            }
        }
        
        return builder.build();
    }
    
    private Entry parseItem(Element entry) {
        EntryBuilder entryBuilder = new EntryBuilder();

        Elements children = entry.getChildElements();
        
        for(int i = 0; i<children.size(); i++) {
            Element child = children.get(i);
            
            if("guid".equals(child.getLocalName())) {
                entryBuilder.id(child.getValue());
            } else if("pubDate".equals(child.getLocalName())) {
                entryBuilder.updated(parseDateTime(child.getValue()));
            } else {
                entryBuilder.custom(child);
            }
        }

        return entryBuilder.build();
    }
}
