package org.djr4488.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.json.bind.annotation.JsonbProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioResponse {
    @JsonbProperty
    private String audioSource;
}
