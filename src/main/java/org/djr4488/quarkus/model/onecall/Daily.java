package org.djr4488.quarkus.model.onecall;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.djr4488.quarkus.model.Weather;
import org.djr4488.quarkus.model.adapters.SecondsAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Daily implements Serializable {
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
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime moonrise;
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime moonset;
    @JsonbProperty("moon_phase")
    private BigDecimal moonPhase;
    @JsonbProperty
    private Long pressure;
    @JsonbProperty
    private Long humidity;
    @JsonbProperty("dew_point")
    private BigDecimal dewpoint;
    @JsonbProperty("wind_speed")
    private BigDecimal windSpeed;
    @JsonbProperty("wind_deg")
    private Long windDeg;
    @JsonbProperty("wind_gust")
    private BigDecimal windGust;
    @JsonbProperty
    private Long clouds;
    @JsonbProperty
    private Long visibility;
    @JsonbProperty
    private BigDecimal uvi;
    @JsonbProperty("pop")
    private BigDecimal probabilityOfPrecipitation;
    @JsonbProperty
    private BigDecimal rain;
    @JsonbProperty
    private BigDecimal snow;
    @JsonbProperty
    private Temp temp;
    @JsonbProperty("feels_like")
    private FeelsLike feelsLike;
    @JsonbProperty
    private List<Weather> weather;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
