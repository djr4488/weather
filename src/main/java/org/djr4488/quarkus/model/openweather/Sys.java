package org.djr4488.quarkus.model.openweather;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.djr4488.quarkus.model.adapters.SecondsAdapter;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.json.bind.annotation.JsonbDateFormat.TIME_IN_MILLIS;

@XmlRootElement(name = "current")
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Sys implements Serializable {
    @XmlElement(name = "id")
    @JsonbProperty("id")
    private Long id;
    @XmlElement(name = "type")
    @JsonbProperty("type")
    private Long sysType;
    @XmlElement(name = "country")
    @JsonbProperty("country")
    private String country;
    @XmlElement(name = "sunrise")
    @JsonbProperty("sunrise")
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime sunrise;
    @XmlElement(name = "sunset")
    @JsonbProperty("sunset")
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime sunset;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
