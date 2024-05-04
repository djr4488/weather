package org.djr4488.quarkus.model.onecall;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class OpenWeatherOneCallResponse implements Serializable {
    @JsonbProperty
    private BigDecimal lat;
    @JsonbProperty
    private BigDecimal lon;
    @JsonbProperty
    private String timezone;
    @JsonbProperty("timezone_offset")
    private Long timezoneOffset;
    @JsonbProperty("name")
    private String place;
    @JsonbProperty
    private Current current;
    @JsonbProperty
    private List<Daily> daily;
    @JsonbProperty
    private List<Hourly> hourly;
    @JsonbProperty
    private List<Alert> alerts;

    public Integer getAlertSize() {
        return (alerts == null || alerts.size() == 0) ? 0 : alerts.size();
    }

    @Override
    public String toString() {
        return JsonbBuilder.create().toJson(this);
    }
}
