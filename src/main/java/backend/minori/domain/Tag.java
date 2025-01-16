package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long TagId;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(targetEntity = AnimeTag.class, cascade = CascadeType.ALL)
    private List<AnimeTag> animeTags;
}
