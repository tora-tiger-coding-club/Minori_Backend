package backend.minori.api.anime.repository;

import backend.minori.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long>, JpaSpecificationExecutor<Anime> {
    List<Anime> findByTitleKrContaining(String keyword);
}
