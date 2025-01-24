package backend.minori.api.episodereview.repository;

import backend.minori.domain.EpisodeReview;
import backend.minori.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeReviewRepository extends JpaRepository<EpisodeReview, Long> {
    List<EpisodeReview> findByAnimeIdAndIsPublicTrue(Long animeId);
    List<EpisodeReview> findByAnimeId(Long animeId);
    List<EpisodeReview> findByUserAndIsPublicTrue(User user);
    Optional<EpisodeReview> findByAnimeIdAndId (Long animeId, Long id);
    Optional<EpisodeReview> findByIdAndAnimeIdAndIsPublicTrue(Long id, Long animeId);
    boolean existsByAnimeIdAndUserAndEpisode(Long animeId, User user, int episode);
}
