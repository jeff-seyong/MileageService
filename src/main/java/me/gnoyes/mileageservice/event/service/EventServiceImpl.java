package me.gnoyes.mileageservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.type.EventType;
import me.gnoyes.mileageservice.constants.type.ResultCodeType;
import me.gnoyes.mileageservice.dto.EventResponse;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.review.service.ReviewService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final ReviewService reviewService;

    public EventResponse distribute(EventDto eventDto) {
        EventType eventType = eventDto.getType();
        switch (eventType) {
            case REVIEW:
                log.info("> Event is distributed to ReviewService");
                return reviewService.distribute(eventDto);
            default:
                throw new ServiceException(ResultCodeType.FAIL_E_001);
        }
    }
}
