package org.djr4488.quarkus.model.rss;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Guid implements Serializable {
    @XmlAttribute(name = "isPermaLink")
    private boolean permaLink;
    @XmlValue
    private String guid;
}
