package org.djr4488.quarkus.model.globe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geometry {
    @JsonbProperty("type")
    private String typ;                 // point
    @JsonbProperty
    List<BigDecimal> coordinates;       // lon, lat, alt, unc
}
