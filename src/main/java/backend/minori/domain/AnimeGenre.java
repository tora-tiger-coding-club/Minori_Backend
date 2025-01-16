package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AnimeGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long TagId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Anime AnimeId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Genre GenreId;
}
