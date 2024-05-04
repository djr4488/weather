package org.djr4488.quarkus.model.onecall;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Temp implements Serializable {
    @JsonbProperty("morn")
    private BigDecimal morning;
    @JsonbProperty("day")
    private BigDecimal day;
    @JsonbProperty("eve")
    private BigDecimal evening;
    @JsonbProperty("night")
    private BigDecimal night;
    @JsonbProperty("min")
    private BigDecimal min;
    @JsonbProperty("max")
    private BigDecimal max;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
