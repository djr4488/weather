package org.djr4488.quarkus.model.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    @Column(name = "weather", length = 250000)
    @Size(max = 249999)
    private String weather;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_search_id")
    private WeatherSearch weatherSearch;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
