package me.gnoyes.mileageservice.review.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.action.EventAction;

@Getter
@NoArgsConstructor
public class UserPointDeleteApplyDto {
    private String userId;
    private String reviewId;
    private EventAction action;

    @Builder
    public UserPointDeleteApplyDto(String userId, String reviewId, EventAction action) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.action = action;
    }

    @Override
    public String toString() {
        return "UserPointDeleteApplyDto{" +
                "userId=" + userId +
                "reviewId=" + reviewId +
                ", action=" + action +
                '}';
    }
}
