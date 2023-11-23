package org.djr4488.quarkus.model.globe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {
    @JsonbProperty
    private String name;
    @JsonbProperty
    private BigDecimal latitude;
    @JsonbProperty
    private BigDecimal longitude;
    @JsonbProperty
    private String temperature;
    @JsonbProperty
    private String html;
}
