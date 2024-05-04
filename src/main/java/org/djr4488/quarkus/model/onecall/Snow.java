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
import java.math.RoundingMode;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Snow implements Serializable {
    @JsonbProperty("1h")
    private String oneHourSnowAmt;

    private String getOneHourSnowAmt() {
        BigDecimal snowAmount = new BigDecimal(oneHourSnowAmt);
        return snowAmount.divide(new BigDecimal("24.5"), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP).toString();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
