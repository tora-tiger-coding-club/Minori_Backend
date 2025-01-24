package backend.minori.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "anime_id", nullable = false)
    private Long animeId;

    @Column(name = "content")
    private String content;

    @Column(name = "star", nullable = false)
    private int star;

    @Column(name = "likes", nullable = false)
    private int likes;

    @Column(name = "is_spoiler", nullable = false)
    private boolean isSpoiler;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    public void updateReview(boolean isSpoiler, boolean isPublic, int star, String content) {
        this.isSpoiler = isSpoiler;
        this.isPublic = isPublic;
        this.star = star;
        this.content = content;
    }

    public void increaseLikes() {
        this.likes += 1;
    }
}
