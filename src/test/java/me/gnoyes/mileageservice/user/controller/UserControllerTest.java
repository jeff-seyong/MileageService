package me.gnoyes.mileageservice.user.controller;

import me.gnoyes.mileageservice.config.AbstractControllerTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractControllerTest {

    @Autowired
    UserController userController;

    @Override
    protected Object controller() {
        return userController;
    }

    @Test
    @Disabled
    @DisplayName("유저 마일리지 조회 API 테스트(/mileage/{userId})")
    void getMileage() throws Exception {
        String userId = "userId-1";
        mockMvc.perform(
                get("/mileage/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


}