package backend.minori.api.record.service;

import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.api.record.dto.AnimeRecordRequestDto;
import backend.minori.api.record.dto.AnimeRecordResponseDto;
import backend.minori.api.record.repository.AnimeRecordRepository;
import backend.minori.api.user.repository.UserRepository;
import backend.minori.domain.Anime;
import backend.minori.domain.AnimeRecord;
import backend.minori.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeRecordService {
    private final AnimeRecordRepository animeRecordRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public List<AnimeRecordResponseDto> getAllAnimeRecordsByUserId(Long userId) {
        List<AnimeRecord> animeRecordList = animeRecordRepository.findAllByUserId(userId);
        return animeRecordList.stream()
                .map(AnimeRecordResponseDto::of)
                .toList();
    }

    public AnimeRecordResponseDto getAnimeRecordById(Long userId, Long recordId) {
        AnimeRecord animeRecord = animeRecordRepository.findByUserIdAndId(userId, recordId);
        return AnimeRecordResponseDto.of(animeRecord);

    }

    public AnimeRecordResponseDto saveAnimeRecord(Long userId, AnimeRecordRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Anime anime = animeRepository.findById(request.getAnimeId())
                .orElseThrow(() -> new IllegalArgumentException("애니메이션을 찾을 수 없습니다."));

        AnimeRecord animeRecord = AnimeRecord.builder()
                .user(user)
                .anime(anime)
                .status(request.getStatus())
                .currentEpisode(request.getCurrentEpisode())
                .startedAt(request.getStartedAt())
                .build();

        AnimeRecord savedAnimeRecord = animeRecordRepository.save(animeRecord);
        return AnimeRecordResponseDto.of(savedAnimeRecord);
    }

    public AnimeRecordResponseDto updateAnimeRecord(Long userId, Long recordId, AnimeRecordRequestDto updatedRequest) {
        AnimeRecord animeRecord = animeRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록을 찾을 수 없습니다."));

        AnimeRecord updatedRecord = animeRecord.update(updatedRequest);
        animeRecordRepository.save(updatedRecord);
        return AnimeRecordResponseDto.of(updatedRecord);
    }

    public void deleteAnimeRecord(Long userId, Long recordId) {
        AnimeRecord animeRecord = animeRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록을 찾을 수 없습니다."));
        animeRecordRepository.delete(animeRecord);
    }
}
