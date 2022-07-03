package me.gnoyes.mileageservice.review.model.dto;

import lombok.Getter;
import me.gnoyes.mileageservice.dto.EventResponse;

@Getter
public class ReviewEventResponseDto implements EventResponse {

    private String userId;
    private int point;

    public ReviewEventResponseDto() {
    }

    public ReviewEventResponseDto(String userId, int point) {
        this.userId = userId;
        this.point = point;
    }
}
