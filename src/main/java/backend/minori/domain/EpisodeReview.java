package backend.minori.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "episode_review")
public class EpisodeReview extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "episode_review_id")
  private Long episodeReviewId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

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

  @Column(name = "episode", nullable = false)
  private int episode;

  @Column(name = "is_public", nullable = false)
  private boolean isPublic;

  public void updateReview(boolean isSpoiler, boolean isPublic, int star) {
    this.isSpoiler = isSpoiler;
    this.isPublic = isPublic;
    this.star = star;
  }

  // 좋아요 증가를 위한 메서드
  public void increaseLikes() {
    this.likes += 1;
  }

}
