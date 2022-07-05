package me.gnoyes.mileageservice.review.service;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.constants.type.EventType;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.event.model.dto.EventDtoBuilder;
import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistoryBuilder;
import me.gnoyes.mileageservice.review.repository.ReviewEventHistoryRepository;
import me.gnoyes.mileageservice.user.model.dto.UserMileageDtoBuilder;
import me.gnoyes.mileageservice.user.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointDeleteApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointModApplyDto;
import me.gnoyes.mileageservice.user.service.UserMileageService;
import me.gnoyes.mileageservice.user.service.UserPointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplLogicTest {
    @InjectMocks
    ReviewServiceImpl reviewService;
    @Mock
    UserPointService userPointService;
    @Mock
    UserMileageService userMileageService;
    @Mock
    ReviewEventHistoryRepository reviewEventHistoryRepository;

    @Test
    void 등록이벤트_정상() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.ADD, reviewId, content, photoIds, userId, placeId);

        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(new ArrayList<>());
        given(reviewEventHistoryRepository.save(any(ReviewEventHistory.class)))
                .willReturn(ReviewEventHistoryBuilder.of(userId, placeId, reviewId, EventAction.ADD, content.length(), photoIds.size(), PointPolicy.STANDARD, now(), now()));
        given(reviewEventHistoryRepository.countByPlaceId(any(String.class)))
                .willReturn(0);
        given(userPointService.addEventApply(any(UserPointAddApplyDto.class)))
                .willReturn(3);
        given(userMileageService.updateMileage(any(String.class), any(Integer.class)))
                .willReturn(UserMileageDtoBuilder.of(userId, 3, now(), now()));

        // when
        ReviewEventResponseDto reviewEventResponseDto = reviewService.distribute(eventDto);

        // then
        assertThat(reviewEventResponseDto.getUserId()).isEqualTo(userId);
        assertThat(reviewEventResponseDto.getPoint()).isEqualTo(3);
        assertThat(reviewEventResponseDto.getTotalMileage()).isEqualTo(3);
    }

    @Test
    void 등록이벤트_해당_장소에_이미_리뷰_등록된_경우() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.ADD, reviewId, content, photoIds, userId, placeId);

        // checkOnlyOneReview 호출 시
        // 해당 장소에 이미 등록된 리뷰가 있는 경우
        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(Collections.singletonList(ReviewEventHistoryBuilder.of(userId, placeId, "already-added-review-id-1", EventAction.ADD, content.length(), photoIds.size(), PointPolicy.STANDARD, now(), now())));
        // then
        assertThrows(ServiceException.class, ()-> reviewService.distribute(eventDto));

        // checkOnlyOneReview 호출 시
        // 해당 장소에 이미 등록된 리뷰가 있는데 수정된적 있는 경우
        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(Collections.singletonList(ReviewEventHistoryBuilder.of(userId, placeId, "already-added-review-id-2", EventAction.MOD, 10, 0, PointPolicy.STANDARD, now(), now())));
        // then
        assertThrows(ServiceException.class, ()-> reviewService.distribute(eventDto));
    }

    @Test
    void 수정이벤트_정상() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 너무 너무 너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.MOD, reviewId, content, photoIds, userId, placeId);

        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(Collections.singletonList(ReviewEventHistoryBuilder.of(userId, placeId, reviewId, EventAction.ADD, 5, 5, PointPolicy.STANDARD, now(), now())));
        given(reviewEventHistoryRepository.save(any(ReviewEventHistory.class)))
                .willReturn(ReviewEventHistoryBuilder.of(userId, placeId, reviewId, EventAction.MOD, content.length(), photoIds.size(), PointPolicy.STANDARD, now(), now()));
        given(userPointService.modEventApply(any(UserPointModApplyDto.class)))
                .willReturn(0);
        given(userMileageService.updateMileage(any(String.class), any(Integer.class)))
                .willReturn(UserMileageDtoBuilder.of(userId, 3, now(), now()));

        // when
        ReviewEventResponseDto reviewEventResponseDto = reviewService.distribute(eventDto);

        // then
        assertThat(reviewEventResponseDto.getUserId()).isEqualTo(userId);
        assertThat(reviewEventResponseDto.getPoint()).isEqualTo(0);
        assertThat(reviewEventResponseDto.getTotalMileage()).isEqualTo(3);
    }

    @Test
    void 수정이벤트_등록된_리뷰가_없거나_이미_삭제된_경우() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 너무 너무 너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.MOD, reviewId, content, photoIds, userId, placeId);

        // then
        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(new ArrayList<>());
        assertThrows(ServiceException.class, ()-> reviewService.distribute(eventDto));

        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(Collections.singletonList(ReviewEventHistoryBuilder.of(userId, placeId, "deleted-review-id-1", EventAction.DELETE, content.length(), photoIds.size(), PointPolicy.STANDARD, now(), now())));
        assertThrows(ServiceException.class, ()-> reviewService.distribute(eventDto));
    }

    @Test
    void 삭제이벤트_정상() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 너무 너무 너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.DELETE, reviewId, content, photoIds, userId, placeId);

        given(reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(any(String.class), any(String.class)))
                .willReturn(Collections.singletonList(ReviewEventHistoryBuilder.of(userId, placeId, reviewId, EventAction.ADD, 5, 5, PointPolicy.STANDARD, now(), now())));
        given(reviewEventHistoryRepository.save(any(ReviewEventHistory.class)))
                .willReturn(ReviewEventHistoryBuilder.of(userId, placeId, reviewId, EventAction.DELETE, content.length(), photoIds.size(), PointPolicy.STANDARD, now(), now()));
        given(userPointService.deleteEventApply(any(UserPointDeleteApplyDto.class)))
                .willReturn(-3);
        given(userMileageService.updateMileage(any(String.class), any(Integer.class)))
                .willReturn(UserMileageDtoBuilder.of(userId, 0, now(), now()));

        // when
        ReviewEventResponseDto reviewEventResponseDto = reviewService.distribute(eventDto);

        // then
        assertThat(reviewEventResponseDto.getUserId()).isEqualTo(userId);
        assertThat(reviewEventResponseDto.getPoint()).isEqualTo(-3);
        assertThat(reviewEventResponseDto.getTotalMileage()).isEqualTo(0);
    }

}