package me.gnoyes.mileageservice.user.model.entity;

import me.gnoyes.mileageservice.constants.type.PointType;

public class UserPointHistoryBuilder {
    public static UserPointHistory of(Long pointHistoryId, String userId, String reviewId, Integer point, PointType reason) {
        return new UserPointHistory(pointHistoryId, userId, reviewId, point, reason);
    }
}