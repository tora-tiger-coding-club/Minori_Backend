package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime animeId;

    @Column(name = "content")
    private String content;

    @Column(name = "star", nullable = false)
    private int star;

    @Column(name = "likes", nullable = false)
    private int like;

    @Column(name = "is_spoiler", nullable = false)
    private boolean isSpoiler;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
}
