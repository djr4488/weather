package org.djr4488.quarkus.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Weather implements Serializable {
    @XmlElement(name = "id")
    @JsonbProperty("id")
    private Long id;
    @XmlElement(name = "main")
    @JsonbProperty("main")
    private String main;
    @XmlElement(name = "description")
    @JsonbProperty("description")
    private String description;
    @XmlElement(name = "icon")
    @JsonbProperty("icon")
    private String icon;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
