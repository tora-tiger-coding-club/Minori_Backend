package backend.minori.api.review.repository;

import backend.minori.domain.Review;
import backend.minori.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAnimeIdAndIsPublicTrue(Long animeId);
    Optional<Review> findByIdAndAnimeIdAndIsPublicTrue(Long id, Long animeId);
    Optional<Review> findByIdAndAnimeId(Long id, Long animeId);
    List<Review> findByAnimeId(Long animeId);
    List<Review> findByUserAndIsPublicTrue(User user);
    boolean existsByAnimeIdAndUser(Long animeId, User user);
}
