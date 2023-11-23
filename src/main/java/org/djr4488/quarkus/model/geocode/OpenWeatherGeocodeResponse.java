package org.djr4488.quarkus.model.geocode;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class OpenWeatherGeocodeResponse implements Serializable {
    @JsonbProperty
    private String zip;
    @JsonbProperty
    private String name;
    @JsonbProperty("local_names")
    private Map<String, String> localNames;
    @JsonbProperty
    private BigDecimal lat;
    @JsonbProperty
    private BigDecimal lon;
    @JsonbProperty
    private String country;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
