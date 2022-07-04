package me.gnoyes.mileageservice.user.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.type.PointType;
import me.gnoyes.mileageservice.user.model.entity.UserPointHistory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserPointHistoryDto {
    private String reviewId;
    private Integer point;
    private PointType reason;
    private LocalDateTime registerDate;

    public UserPointHistoryDto(UserPointHistory entity) {
        this.reviewId = entity.getReviewId();
        this.point = entity.getPoint();
        this.reason = entity.getReason();
        this.registerDate = entity.getRegisterDate();
    }
}
