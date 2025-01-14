package backend.minori.api.record.service;

import backend.minori.api.record.dto.AnimationRecord;
import backend.minori.api.record.AnimationRecordRepository;
import backend.minori.api.record.dto.AnimationRecordDTO;
import backend.minori.api.record.mapper.AnimationRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimationRecordService {
    @Autowired
    private final AnimationRecordRepository animationRecordRepository;

    public List<AnimationRecordDTO> getRecordsByUserId(Long userId) {
        return animationRecordRepository.findByUserId(userId).stream()
                .map(AnimationRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AnimationRecordDTO saveRecord(Long userId, AnimationRecordDTO recordDTO) {
        AnimationRecord record = AnimationRecordMapper.toEntity(recordDTO);
        record.setUserId(userId);
        return AnimationRecordMapper.toDTO(animationRecordRepository.save(record));
    }

    public AnimationRecordDTO getRecordById(Long userId, Long recordId) {
        return AnimationRecordMapper.toDTO(animationRecordRepository.findByUserIdAndRecordId(userId, recordId));
    }

    public AnimationRecordDTO updateRecord(Long userId, Long recordId, AnimationRecordDTO updatedRecordDTO) {
        AnimationRecord updatedRecord = AnimationRecordMapper.toEntity(updatedRecordDTO);
        AnimationRecord record = animationRecordRepository.findByUserIdAndRecordId(userId, recordId);
        if (record != null) {
            record.setAnimationTitle(updatedRecord.getAnimationTitle());
            record.setWatchedDate(updatedRecord.getWatchedDate());
            return AnimationRecordMapper.toDTO(animationRecordRepository.save(record));
        }
        return null;
    }

    public void deleteRecord(Long userId, Long recordId) {
        animationRecordRepository.deleteById(recordId);
    }

    public String shareRecord(Long userId, Long recordId) {
        AnimationRecordDTO recordDTO = AnimationRecordMapper.toDTO(animationRecordRepository.findByUserIdAndRecordId(userId, recordId));
        return "애니메이션 시청 기록: " + recordDTO.getAnimationTitle() + ", 날짜: " + recordDTO.getWatchedDate();
    }
}
