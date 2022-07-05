package me.gnoyes.mileageservice.user.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class UserMileageBuilder {
    public static UserMileage of(String userId, Integer mileage, List<UserPointHistory> mileageHistory, LocalDateTime registerDate, LocalDateTime updateDate) {
        return new UserMileage(userId, mileage, mileageHistory, registerDate, updateDate);
    }
}