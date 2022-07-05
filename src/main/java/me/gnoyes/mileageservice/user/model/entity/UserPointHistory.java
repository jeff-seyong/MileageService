package me.gnoyes.mileageservice.user.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.type.PointType;
import me.gnoyes.mileageservice.review.model.entity.BaseInformation;

import javax.persistence.*;

@Entity
@Table(name = "user_point_history")
@Getter
@NoArgsConstructor
public class UserPointHistory extends BaseInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", nullable = false, columnDefinition = "유저 포인트 히스토리 아이디")
    private Long pointHistoryId;

    @Column(name = "user_id", nullable = false, length = 36, columnDefinition = "리뷰 작성자 아이디")
    private String userId;

    @Column(name = "review_id", nullable = false, length = 36, columnDefinition = "리뷰 아이디")
    private String reviewId;

    @Column(name = "point", nullable = false, columnDefinition = "적립/차감 포인트")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, columnDefinition = "포인트 적립/차감 사유")
    private PointType reason;

    public UserPointHistory(String userId, String reviewId, Integer point, PointType reason) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.point = point;
        this.reason = reason;
    }

    // for Test
    UserPointHistory(Long pointHistoryId, String userId, String reviewId, Integer point, PointType reason) {
        this.pointHistoryId = pointHistoryId;
        this.userId = userId;
        this.reviewId = reviewId;
        this.point = point;
        this.reason = reason;
    }
}
