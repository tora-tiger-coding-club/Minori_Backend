package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long GenreId;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(targetEntity = AnimeGenre.class, cascade = CascadeType.ALL)
    private List<AnimeGenre> animeGenres;
}
