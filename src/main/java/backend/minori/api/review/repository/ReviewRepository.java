package backend.minori.api.review.repository;

import backend.minori.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 애니메이션 리뷰 목록 조회
    List<Review> findByAnimeId(Long animeId);

    // 유저 애니메이션 리뷰 조회
    List<Review> findByUserId(Long userId);

    // 특정 애니메이션의 특정 리뷰 찾기 (수정, 삭제, 공유에 사용하기 위함)
    Optional<Review> findByAnimeIdAndReviewId(Long animeId, Long reviewId);
}
