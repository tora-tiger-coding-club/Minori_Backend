package backend.minori.api.record;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimationRecordRepository extends JpaRepository<AnimationRecord, Long> {
    List<AnimationRecord> findByUserId(Long userId);
    AnimationRecord findByUserIdAndRecordId(Long userId, Long recordId);
}
