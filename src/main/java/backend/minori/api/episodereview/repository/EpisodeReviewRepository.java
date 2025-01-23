package backend.minori.api.episodereview.repository;
import backend.minori.domain.EpisodeReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeReviewRepository extends JpaRepository<EpisodeReview, Long> {
    List<EpisodeReview> findByAnimeId(Long animeId);
    List<EpisodeReview> findByUserId(Long userId);
    Optional<EpisodeReview> findByAnimeIdAndEpisodeReviewId(Long animeId, Long episodeReviewId);
}
