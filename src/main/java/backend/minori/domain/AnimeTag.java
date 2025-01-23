package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AnimeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long AnimeTagId;

    @ManyToOne
    @JoinColumn
    private Anime AnimeId;

    @ManyToOne
    @JoinColumn
    private Tag TagId;
}
