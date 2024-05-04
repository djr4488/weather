package org.djr4488.quarkus.model.rss;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Channel implements Serializable {
    @XmlElement
    private String title;
    @XmlElement
    private String link;
    @XmlElement
    private String description;
    @XmlElement
    private String language;
    @XmlElement
    private String copyright;
    @XmlElement
    private List<Item> item;
}
