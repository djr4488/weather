package org.djr4488.quarkus.model.onecall;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.djr4488.quarkus.model.Weather;
import org.djr4488.quarkus.model.adapters.SecondsAdapter;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Current implements Serializable {
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime dt;
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime sunrise;
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime sunset;
    @JsonbProperty
    private BigDecimal temp;
    @JsonbProperty("feels_like")
    private BigDecimal feelsLike;
    @JsonbProperty
    private Long pressure;
    @JsonbProperty
    private Long humidity;
    @JsonbProperty("dew_point")
    private BigDecimal dewpoint;
    @JsonbProperty
    private BigDecimal uvi;
    @JsonbProperty
    private Long clouds;
    @JsonbProperty
    private Long visibility;
    @JsonbProperty("wind_speed")
    private BigDecimal windSpeed;
    @JsonbProperty("wind_deg")
    private Long windDeg;
    @JsonbProperty
    private Long offsetWindDeg;
    @JsonbProperty("wind_gust")
    private BigDecimal windGust;
    @JsonbProperty
    private List<Weather> weather;
    @JsonbProperty
    private Rain rain;
    @JsonbProperty
    private Snow snow;
    @JsonbProperty
    private Boolean isDaytime;

    public Long getOffsetWindDeg() {
        return windDeg >=0 && windDeg < 180 ? windDeg + 180 : windDeg - 180;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}

