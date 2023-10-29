package org.djr4488.quarkus.model.globe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    @JsonbProperty
    private Properties properties;
    @JsonbProperty
    private Geometry geometry;
}
