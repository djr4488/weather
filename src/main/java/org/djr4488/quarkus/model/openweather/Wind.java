package org.djr4488.quarkus.model.openweather;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Wind implements Serializable {
    @XmlElement(name = "speed")
    private Double speed;
    @XmlElement(name = "deg")
    private BigDecimal deg;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
