package me.gnoyes.mileageservice.event.controller;

import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.event.dto.EventDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EventController {

    @PostMapping("/events")
    public void onEvent(@RequestBody EventDto eventDto) {
        log.info("> EventController.onEvent");
        log.info("> eventDto: {}", eventDto);
    }
}
