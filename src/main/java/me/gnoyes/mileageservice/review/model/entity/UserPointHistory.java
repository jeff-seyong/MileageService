package me.gnoyes.mileageservice.review.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.type.PointType;

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

    @Column(name = "event_history_id", nullable = false, columnDefinition = "리뷰 이벤트 히스토리 아이디")
    private Long eventHistoryId;

    @Column(name = "point", nullable = false, columnDefinition = "적립/차감 포인트")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, columnDefinition = "포인트 적립/차감 사유")
    private PointType reason;

    public UserPointHistory(Long eventHistoryId, Integer point, PointType reason) {
        this.eventHistoryId = eventHistoryId;
        this.point = point;
        this.reason = reason;
    }
}
