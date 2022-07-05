package me.gnoyes.mileageservice.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gnoyes.mileageservice.config.AbstractControllerTest;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.type.EventType;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.event.model.dto.EventDtoBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventControllerTest extends AbstractControllerTest {

    @Autowired
    EventController eventController;

    private String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
    private String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
    private String reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772";
    private String photoId1 = "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8";
    private String photoId2 = "fb0cef2851d-4a50-bb07-9cc15cbdc332";

    @Override
    protected Object controller() {
        return eventController;
    }

    private String asString(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Disabled
    void 리뷰_등록_이벤트_API() throws Exception {
        // given
        String content = "너무 좋은것 같아요!";
        List<String> photoIds = new ArrayList<>();
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.ADD, reviewId, content, photoIds, userId, placeId);

        // when & then
        mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asString(eventDto))
        ).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void 리뷰_수정_이벤트_API() throws Exception {
        // given
        String content = "너무 좋은것 같아요! 너무 좋아서 사진도 첨부해요~~";
        List<String> photoIds = Arrays.asList(photoId1, photoId2);
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.MOD, reviewId, content, photoIds, userId, placeId);

        // when & then
        mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asString(eventDto))
        ).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void 리뷰_삭제_이벤트_API() throws Exception {
        // given
        String content = "너무 좋은것 같아요! 너무 좋아서 사진도 첨부해요~~";
        List<String> photoIds = Arrays.asList(photoId1, photoId2);
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.DELETE, reviewId, content, photoIds, userId, placeId);

        // when & then
        mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asString(eventDto))
        ).andExpect(status().isOk());
    }

}