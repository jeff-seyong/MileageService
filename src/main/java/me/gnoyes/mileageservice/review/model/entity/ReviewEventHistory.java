package me.gnoyes.mileageservice.review.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.event.model.dto.EventDto;

import javax.persistence.*;

@Entity
@Table(name = "review_event_history")
@Getter
@NoArgsConstructor
public class ReviewEventHistory extends BaseInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_history_id", nullable = false, columnDefinition = "리뷰 이벤트 히스토리 아이디")
    private Long eventHistoryId;

    @Column(name = "user_id", nullable = false, length = 36, columnDefinition = "리뷰 작성자 아이디")
    private String userId;

    @Column(name = "place_id", nullable = false, length = 36, columnDefinition = "리뷰가 작성된 장소 아이디")
    private String placeId;

    @Column(name = "review_id", nullable = false, length = 36, columnDefinition = "리뷰 아이디")
    private String reviewId;

    @Column(name = "action", nullable = false, length = 10, columnDefinition = "리뷰 이벤트 종류")
    @Enumerated(value = EnumType.STRING)
    private EventAction action;

    @Column(name = "contents_size", columnDefinition = "리뷰 내용 길이")
    private int contentsSize;

    @Column(name = "photo_count", columnDefinition = "첨부 이미지 개수")
    private int photoCount;

    @Column(name = "point_policy", columnDefinition = "적용된 포인트 적립 정책")
    @Enumerated(value = EnumType.STRING)
    private PointPolicy pointPolicy;

    public ReviewEventHistory(EventDto eventDto) {
        this.userId = eventDto.getUserId();
        this.placeId = eventDto.getPlaceId();
        this.reviewId = eventDto.getReviewId();
        this.action = eventDto.getAction();
        this.contentsSize = eventDto.getContent().length();
        this.photoCount = eventDto.getAttachedPhotoIds().size();
    }

    public void recordPointPolicy(PointPolicy pointPolicy) {
        this.pointPolicy = pointPolicy;
    }
}
