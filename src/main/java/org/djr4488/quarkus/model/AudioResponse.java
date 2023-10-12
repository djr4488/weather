package org.djr4488.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioResponse {
    @JsonbProperty
    private String audioSource;
}
