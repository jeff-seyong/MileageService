package me.gnoyes.mileageservice.user.model.dto;

import java.time.LocalDateTime;

public class UserMileageDtoBuilder {
    public static UserMileageDto of(String userId, Integer mileage, LocalDateTime registerDate, LocalDateTime updateDate) {
        return new UserMileageDto(userId, mileage, registerDate, updateDate);
    }
}