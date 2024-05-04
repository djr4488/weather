package org.djr4488.quarkus.model.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "tiles",
    uniqueConstraints = @UniqueConstraint(name = "udx_tiles_sxyz", columnNames = { "s", "x", "y", "z" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = "findTileBySXYZ",
                query = "SELECT tl FROM Tile tl where tl.s = :s and tl.x = :x and tl.y = :y and tl.z = :z")
})
public class Tile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    @Column(name = "s")
    private String s;
    @Column(name = "x")
    private Integer x;
    @Column(name = "y")
    private Integer y;
    @Column(name = "z")
    private Integer z;
    @Column(name = "image_data", length = 1500000)
    @Lob
    private byte[] imageData;
}
