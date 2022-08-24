package org.djr4488.quarkus.model.openweather;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.djr4488.quarkus.model.Weather;
import org.djr4488.quarkus.model.adapters.SecondsAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class OpenWeatherResponse implements Serializable {
    @XmlElement(name = "name")
    @JsonbProperty("name")
    private String name;
    @XmlElement(name = "timezone")
    @JsonbProperty("timezone")
    private Long timezone;
    @XmlElement(name = "id")
    @JsonbProperty("id")
    private Long id;
    @XmlElement(name = "cod")
    @JsonbProperty("cod")
    private Long cod;
    @XmlElement(name = "base")
    @JsonbProperty("base")
    private String base;
    @XmlElement(name = "visibility")
    @JsonbProperty("visibility")
    private Long visibility;
    @XmlElement(name = "dt")
    @JsonbProperty("dt")
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime dt;
    @XmlElement(name = "coord")
    @JsonbProperty("coord")
    private Coord coord;
    @XmlElement(name = "main")
    @JsonbProperty("main")
    private Main main;
    @XmlElement(name = "wind")
    @JsonbProperty("wind")
    private Wind wind;
    @XmlElement(name = "clouds")
    @JsonbProperty("clouds")
    private Clouds clouds;
    @XmlElement(name = "sys")
    @JsonbProperty("sys")
    private Sys sys;
    @XmlElementWrapper(name = "weather")
    @XmlElement(name = "weather")
    @JsonbProperty("weather")
    private List<Weather> weather;
    @JsonbProperty("fixedScroll")
    private Integer total = 5;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
