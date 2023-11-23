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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_location")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "location", length = 5000)
    @Size(max = 4998)
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
