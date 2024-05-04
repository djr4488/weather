package org.djr4488.quarkus.model.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracks", indexes = {
        @Index(name = "idxTrack_createdAt", columnList = "created_at")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "findTracksSince",
                    query = "SELECT trk FROM Track trk where trk.localDateTime >= :localDateTime")
})
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lon")
    private String lon;
    @Column(name = "altitude")
    private String altitude;
    @Column(name = "heading")
    private String heading;
    @Column(name = "speed")
    private String speed;
    @ManyToOne
    @JoinColumn(name = "weather_location_id")
    private WeatherLocation weatherLocation;
}
