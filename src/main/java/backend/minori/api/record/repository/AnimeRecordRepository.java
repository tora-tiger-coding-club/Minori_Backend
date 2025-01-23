package backend.minori.api.record.repository;

import backend.minori.domain.AnimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimeRecordRepository extends JpaRepository<AnimeRecord, Long> {
    List<AnimeRecord> findAllByUserId(Long userId);
    Optional<AnimeRecord> findByUserIdAndId(Long userId, Long id);
}
