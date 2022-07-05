package me.gnoyes.mileageservice.event.model.dto;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.type.EventType;

import java.util.List;

public class EventDtoBuilder {
    public static EventDto of(EventType type, EventAction action, String reviewId, String content, List<String> attachedPhotoIds, String userId, String placeId) {
        return new EventDto(type, action, reviewId, content, attachedPhotoIds, userId, placeId);
    }
}