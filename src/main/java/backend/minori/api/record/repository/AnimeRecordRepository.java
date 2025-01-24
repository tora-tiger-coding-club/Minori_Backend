package backend.minori.api.record.repository;

import backend.minori.domain.AnimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRecordRepository extends JpaRepository<AnimeRecord, Long> {
    List<AnimeRecord> findAllByUserId(Long userId);
    AnimeRecord findByUserIdAndId(Long userId, Long id);
}
