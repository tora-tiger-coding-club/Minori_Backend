package backend.minori.api.record.mapper;

import backend.minori.api.record.dto.AnimationRecord;
import backend.minori.api.record.dto.AnimationRecordDTO;

public class AnimationRecordMapper {
    public static AnimationRecordDTO toDTO(AnimationRecord entity) {
        AnimationRecordDTO dto = new AnimationRecordDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setAnimationTitle(entity.getAnimationTitle());
        dto.setWatchedDate(entity.getWatchedDate());
        return dto;
    }
    public static AnimationRecord toEntity(AnimationRecordDTO dto) {
        AnimationRecord entity = new AnimationRecord();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setAnimationTitle(dto.getAnimationTitle());
        entity.setWatchedDate(dto.getWatchedDate());
        return entity;
    }
}
