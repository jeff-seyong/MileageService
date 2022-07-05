package me.gnoyes.mileageservice.review.model.entity;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;

import java.time.LocalDateTime;

public class ReviewEventHistoryBuilder {
    public static ReviewEventHistory of(String userId, String placeId, String reviewId, EventAction action, int contentsSize, int photoCount, PointPolicy pointPolicy, LocalDateTime registerDate, LocalDateTime updateDate) {
        return new ReviewEventHistory(userId, placeId, reviewId, action, contentsSize, photoCount, pointPolicy, registerDate, updateDate);
    }
}