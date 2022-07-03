package me.gnoyes.mileageservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.dto.CommonResponse;
import me.gnoyes.mileageservice.dto.EventResponse;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<CommonResponse<EventResponse>> onEvent(@RequestBody EventDto eventDto) {
        log.debug("> EventController.onEvent");
        log.info("> eventDto: {}", eventDto);
        return ResponseEntity.ok(
                new CommonResponse<>(eventService.distribute(eventDto))
        );
    }
}
