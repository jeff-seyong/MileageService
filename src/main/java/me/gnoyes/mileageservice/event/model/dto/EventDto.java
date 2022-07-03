package me.gnoyes.mileageservice.event.model.dto;

import lombok.Getter;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.type.EventType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventDto {
    private EventType type;
    private EventAction action;
    private String reviewId;
    private String content;
    private final List<String> attachedPhotoIds = new ArrayList<>();
    private String userId;
    private String placeId;

    @Override
    public String toString() {
        return "EventDto{" +
                "type=" + type +
                ", action=" + action +
                ", reviewId='" + reviewId + '\'' +
                ", content='" + content + '\'' +
                ", attachedPhotoIds=" + attachedPhotoIds +
                ", userId='" + userId + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
