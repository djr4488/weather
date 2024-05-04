package org.djr4488.quarkus.model.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data", indexes = {
        @Index(name = "idxWeatherData_createdAt", columnList = "created_at")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "findMostRecentWeatherDataForLocation",
                    query = """
                                SELECT wd FROM WeatherData wd 
                                JOIN WeatherLocation wl on wd.weatherSearch = wl.weatherSearch
                                WHERE 
                                    wl.locationName = :location
                                    AND wd.localDateTime = (SELECT max(wd1.localDateTime) FROM WeatherData wd1 
                                                            JOIN WeatherLocation wl1 on wd1.weatherSearch = wl1.weatherSearch
                                                            WHERE 
                                                                wl1.locationName = :location
                                                                AND wd1.localDateTime >= :offsetDateTime)""")
})
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
