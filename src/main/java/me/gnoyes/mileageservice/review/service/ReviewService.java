package me.gnoyes.mileageservice.review.service;

import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;

public interface ReviewService {
    ReviewEventResponseDto distribute(EventDto eventDto);
}
