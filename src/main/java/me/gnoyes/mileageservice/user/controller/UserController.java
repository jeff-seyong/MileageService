package me.gnoyes.mileageservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;
import me.gnoyes.mileageservice.user.service.UserMileageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserMileageService userMileageService;

    @GetMapping("mileage/{userId}")
    public ResponseEntity<UserMileageDto> getMileage(@PathVariable(name = "userId") String userId) {
        log.info("> userId = " + userId);
        return ResponseEntity.ok(userMileageService.getUserMileage(userId));
    }
}
