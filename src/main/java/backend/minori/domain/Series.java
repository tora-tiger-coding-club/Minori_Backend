package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long seriesId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;
}
