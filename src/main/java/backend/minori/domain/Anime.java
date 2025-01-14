package backend.minori.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "anime")
public class Anime extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long animeId;

    @Column(nullable = false, length = 100)
    private String titleKr;

    @Column(length = 100)
    private String titleEn;

    @Column(length = 100)
    private String titleJp;

    @Column(nullable = false)
    private String airingStatus;

    @Column(length = 10)
    private String airingDay;

    private Integer totalEpisodeNumber;

    @Column(length = 30)
    private String production;

    @Column(length = 20)
    private String platform;

    @Column(length = 500)
    private String storyDescription;

    @Column(length = 20)
    private String director;

    @Column(length = 10)
    private String airingType;

    @ManyToOne
    @JoinColumn(name = "seried_id")
    private Series series;
}
