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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Daily implements Serializable {
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime dt;
    @JsonbProperty
    private String dayOfWeek;
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
    @JsonbProperty("moonPhaseImage")
    private String moonPhaseImage;
    @JsonbProperty("moonPhaseValue")
    private String moonPhaseValue;
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
    @JsonbProperty
    private Long offsetWindDeg;
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
    private String summary;
    @JsonbProperty
    private List<Weather> weather;

    public String getDayOfWeek() {
        return dt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public BigDecimal getPrecipInches() {
        final BigDecimal precipInchesMultiplier = new BigDecimal("0.0393701");
        final BigDecimal precipInches;
        if (rain != null) {
            precipInches = rain.multiply(precipInchesMultiplier.setScale(2, RoundingMode.HALF_UP));
        } else if (snow != null) {
            precipInches = snow.multiply(precipInchesMultiplier.setScale(2, RoundingMode.HALF_UP));
        } else {
            precipInches = BigDecimal.ZERO;
        }
        return precipInches;
    }

    /**
     * if (moonPhase == 0) {
     *                   moonphaseValue = 'new moon';
     *                   moonPhaseImg = 'moon-new.svg';
     *                 } else if (moonPhase > 0 && moonPhase < 0.25) {
     *                   moonphaseValue = 'waxing crescent';
     *                   moonPhaseImg = 'moon-waxing-crescent.svg';
     *                 } else if (moonPhase == .25) {
     *                   moonphaseValue = 'first quarter';
     *                   moonPhaseImg = 'moon-first-quarter.svg';
     *                 } else if (moonPhase > .25 && moonPhase < 0.5) {
     *                   moonphaseValue = 'waxing gibbous';
     *                   moonPhaseImg = 'moon-waxing-gibbous.svg';
     *                 } else if (moonPhase == .5) {
     *                   moonphaseValue = 'full moon';
     *                   moonPhaseImg = 'moon-full.svg';
     *                 } else if (moonPhase > .5 && moonPhase < 0.75) {
     *                   moonphaseValue = 'waning gibbous';
     *                   moonPhaseImg = 'moon-waning-gibbous.svg';
     *                 } else if (moonPhase == .75) {
     *                   moonphaseValue = 'last quarter';
     *                   moonPhaseImg = 'moon-last-quarter.svg';
     *                 } else if (moonPhase > .75 && moonPhase < 1.0) {
     *                   moonphaseValue = 'waning crescent';
     *                   moonPhaseImg = 'moon-waning-crescent.svg';
     *                 }
     */
    public String getMoonPhaseImage() {
        String moonPhaseImg = "";
        if (moonPhase.floatValue() == 0) {
            moonPhaseImg = "moon-new.svg";
        } else if (moonPhase.floatValue() > 0 && moonPhase.floatValue() < 0.25) {
            moonPhaseImg = "moon-waxing-crescent.svg";
        } else if (moonPhase.floatValue() == .25) {
            moonPhaseImg = "moon-first-quarter.svg";
        } else if (moonPhase.floatValue() > .25 && moonPhase.floatValue() < 0.5) {
            moonPhaseImg = "moon-waxing-gibbous.svg";
        } else if (moonPhase.floatValue() == .5) {
            moonPhaseImg = "moon-full.svg";
        } else if (moonPhase.floatValue() > .5 && moonPhase.floatValue() < 0.75) {
            moonPhaseImg = "moon-waxing-gibbous.svg";
        } else if (moonPhase.floatValue() == .75) {
            moonPhaseImg = "moon-last-quarter.svg";
        } else if (moonPhase.floatValue() > .75 && moonPhase.floatValue() < 1.0) {
            moonPhaseImg = "moon-waning-crescent.svg";
        }
        return moonPhaseImg;
    }
    
    public String getMoonPhaseValue() {
        String moonphaseValue = "";
        if (moonPhase.floatValue() == 0) {
            moonphaseValue = "new moon";
        } else if (moonPhase.floatValue() > 0 && moonPhase.floatValue() < 0.25) {
            moonphaseValue = "waxing crescent";
        } else if (moonPhase.floatValue() == .25) {
            moonphaseValue = "first quarter";
        } else if (moonPhase.floatValue() > .25 && moonPhase.floatValue() < 0.5) {
            moonphaseValue = "waxing gibbous";
        } else if (moonPhase.floatValue() == .5) {
            moonphaseValue = "full moon";
        } else if (moonPhase.floatValue() > .5 && moonPhase.floatValue() < 0.75) {
            moonphaseValue = "waning gibbous";
        } else if (moonPhase.floatValue() == .75) {
            moonphaseValue = "last quarter";
        } else if (moonPhase.floatValue() > .75 && moonPhase.floatValue() < 1.0) {
            moonphaseValue = "waning crescent";
        }
        return moonphaseValue;
    }

    public Long getOffsetWindDeg() {
        return windDeg >= 0 && windDeg < 180 ? windDeg + 180 : windDeg - 180;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
