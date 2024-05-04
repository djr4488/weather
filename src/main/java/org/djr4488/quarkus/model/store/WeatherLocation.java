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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_location", indexes = {
        @Index(name = "idxWeatherLocation_LatLon", columnList = "lat, lon")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "findWeatherLocationByZipCode",
                query = "select weatherLoc from WeatherLocation weatherLoc where weatherLoc.weatherSearch.location = :zipCode"),
        @NamedQuery(name = "findWeatherLocationByLatLon",
                query = "select weatherLoc from WeatherLocation weatherLoc where weatherLoc.lat = :lat and weatherLoc.lon = :lon"),
        @NamedQuery(name = "findAllDistinctWeatherLocations",
                query = """
                        select distinct weatherLoc.locationName from WeatherLocation weatherLoc
                        """)
})
public class WeatherLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    @Column(name = "location", length = 250000)
    @Size(max = 249998)
    private String location;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lon")
    private String lon;
    @Column(name = "location_name")
    private String locationName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_search_id")
    private WeatherSearch weatherSearch;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
