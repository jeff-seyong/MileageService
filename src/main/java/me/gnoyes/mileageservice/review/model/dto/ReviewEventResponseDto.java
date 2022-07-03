package me.gnoyes.mileageservice.review.model.dto;

import lombok.Getter;
import me.gnoyes.mileageservice.dto.EventResponse;

@Getter
public class ReviewEventResponseDto implements EventResponse {
    private String str;

    public ReviewEventResponseDto() {
        this.str = "str";
    }
}
