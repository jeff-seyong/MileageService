package me.gnoyes.mileageservice.event.service;

import me.gnoyes.mileageservice.dto.EventResponse;
import me.gnoyes.mileageservice.event.model.dto.EventDto;

public interface EventService {

    EventResponse distribute(EventDto eventDto);
}
