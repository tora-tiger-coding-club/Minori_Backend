package backend.minori.domain;

import backend.minori.api.record.dto.AnimeRecordRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long recordId;

    @Column(nullable = false, length = 10)
    private String status;

    @Column
    private Integer currentEpisode;

    @Column
    private LocalDate startedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    public AnimeRecord update(AnimeRecordRequestDto updatedRequest) {
        return AnimeRecord.builder()
                .recordId(this.recordId)
                .status(updatedRequest.getStatus())
                .currentEpisode(updatedRequest.getCurrentEpisode())
                .startedAt(this.startedAt)
                .user(this.user)
                .anime(this.anime)
                .build();
    }
}
