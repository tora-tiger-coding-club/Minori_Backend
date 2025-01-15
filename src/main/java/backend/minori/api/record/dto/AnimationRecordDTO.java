package backend.minori.api.record.dto;

public class AnimationRecordDTO {
    private Long id;
    private Long userId;
    private String animationTitle;
    private String watchedDate;

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnimationTitle() {
        return animationTitle;
    }

    public void setAnimationTitle(String animationTitle) {
        this.animationTitle = animationTitle;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
