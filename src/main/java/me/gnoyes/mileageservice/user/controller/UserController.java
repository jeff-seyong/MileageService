package me.gnoyes.mileageservice.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("mileage/{userId}")
    public void getMileage(@PathVariable(name = "userId") String userId){
        log.info("> UserController.getMileage");
        log.info("userId = " + userId);
    }
}
