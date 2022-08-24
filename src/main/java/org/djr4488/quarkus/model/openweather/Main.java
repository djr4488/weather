package org.djr4488.quarkus.model.openweather;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Main implements Serializable {
    @XmlElement(name = "temp")
    @JsonbProperty("temp")
    private BigDecimal temp;
    @XmlElement(name = "feels_like")
    @JsonbProperty("feels_like")
    private BigDecimal feels_like;
    @XmlElement(name = "temp_min")
    @JsonbProperty("temp_min")
    private BigDecimal temp_min;
    @XmlElement(name = "temp_max")
    @JsonbProperty("temp_max")
    private BigDecimal temp_max;
    @XmlElement(name = "pressure")
    @JsonbProperty("pressure")
    private BigDecimal pressure;
    @XmlElement(name = "humidity")
    @JsonbProperty("humidity")
    private BigDecimal humidity;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
