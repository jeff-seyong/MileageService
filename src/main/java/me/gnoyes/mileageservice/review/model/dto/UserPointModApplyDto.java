package me.gnoyes.mileageservice.review.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.action.EventAction;

@Getter
@NoArgsConstructor
public class UserPointModApplyDto {
    private String userId;
    private String reviewId;
    private EventAction action;
    private int oldContentsSize;
    private int newContentsSize;
    private int oldPhotoCount;
    private int newPhotoCount;

    @Builder
    public UserPointModApplyDto(String userId, String reviewId, EventAction action, int oldContentsSize, int newContentsSize, int oldPhotoCount, int newPhotoCount) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.action = action;
        this.oldContentsSize = oldContentsSize;
        this.newContentsSize = newContentsSize;
        this.oldPhotoCount = oldPhotoCount;
        this.newPhotoCount = newPhotoCount;
    }

    @Override
    public String toString() {
        return "UserPointModApplyDto{" +
                "userId=" + userId +
                "reviewId=" + reviewId +
                ", action=" + action +
                ", oldContentsSize=" + oldContentsSize +
                ", newContentsSize=" + newContentsSize +
                ", oldPhotoCount=" + oldPhotoCount +
                ", newPhotoCount=" + newPhotoCount +
                '}';
    }
}
