package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    public ReviewEventResponseDto distribute(EventDto eventDto) {
        EventAction action = eventDto.getAction();
        switch (action) {
            case ADD:
                onAddEvent(eventDto);
                return new ReviewEventResponseDto();
            case MOD:
                onModEvent(eventDto);
                return new ReviewEventResponseDto();
            case DELETE:
                onDeleteEvent(eventDto);
                return new ReviewEventResponseDto();
            default:
                throw new RuntimeException();
        }
    }

    public void onAddEvent(EventDto eventDto) {
        log.info("ReviewServiceImpl.onAddEvent");
    }

    public void onModEvent(EventDto eventDto) {
        log.info("ReviewServiceImpl.onModEvent");
    }

    public void onDeleteEvent(EventDto eventDto) {
        log.info("ReviewServiceImpl.onDeleteEvent");
    }
}
