package me.gnoyes.mileageservice.review.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.action.EventAction;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class UserPointAddApplyDto {
    private String reviewId;
    private EventAction action;
    private int contentsSize;
    private int photoCount;
    private boolean bonusFlag;

    @Builder
    public UserPointAddApplyDto(String reviewId, EventAction action, int contentsSize, int photoCount, Boolean bonusFlag) {
        this.reviewId = reviewId;
        this.action = action;
        this.contentsSize = contentsSize;
        this.photoCount = photoCount;
        this.bonusFlag = !Objects.isNull(bonusFlag) && bonusFlag;
    }

    @Override
    public String toString() {
        return "UserPointAddApplyDto{" +
                "reviewId=" + reviewId +
                ", action=" + action +
                ", contentsSize=" + contentsSize +
                ", photoCount=" + photoCount +
                ", bonusFlag=" + bonusFlag +
                '}';
    }
}
