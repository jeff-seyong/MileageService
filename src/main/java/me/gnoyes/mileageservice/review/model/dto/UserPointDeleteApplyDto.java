package me.gnoyes.mileageservice.review.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.action.EventAction;

@Getter
@NoArgsConstructor
public class UserPointDeleteApplyDto {
    private String reviewId;
    private EventAction action;

    @Builder
    public UserPointDeleteApplyDto(String reviewId, EventAction action) {
        this.reviewId = reviewId;
        this.action = action;
    }

    @Override
    public String toString() {
        return "UserPointDeleteApplyDto{" +
                "reviewId=" + reviewId +
                ", action=" + action +
                '}';
    }
}