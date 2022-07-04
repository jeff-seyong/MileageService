package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.type.ResultCodeType;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointDeleteApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointModApplyDto;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import me.gnoyes.mileageservice.review.repository.ReviewEventHistoryRepository;
import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;
import me.gnoyes.mileageservice.user.service.UserMileageService;
import me.gnoyes.mileageservice.user.service.UserPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewEventHistoryRepository reviewEventHistoryRepository;
    private final UserPointService userPointService;
    private final UserMileageService userMileageService;

    @Transactional
    public ReviewEventResponseDto distribute(EventDto eventDto) {
        EventAction action = eventDto.getAction();
        switch (action) {
            case ADD:
                return onAddEvent(eventDto);
            case MOD:
                return onModEvent(eventDto);
            case DELETE:
                return onDeleteEvent(eventDto);
            default:
                throw new ServiceException(ResultCodeType.FAIL_R_001);
        }
    }

    public ReviewEventResponseDto onAddEvent(EventDto eventDto) {
        log.info("> [Review Add] eventDto: {}", eventDto);

        String userId = eventDto.getUserId();
        String placeId = eventDto.getPlaceId();
        checkOnlyOneReview(userId, placeId);

        boolean bonusFlag = checkFirstReview(placeId);
        log.info("> userId: {}, placeId: {}, bonusFlag: {}", userId, placeId, bonusFlag);
        ReviewEventHistory reviewEventHistory = appendReviewEventHistory(new ReviewEventHistory(eventDto));

        UserPointAddApplyDto userPointAddApplyDto = UserPointAddApplyDto.builder()
                .userId(userId)
                .reviewId(reviewEventHistory.getReviewId())
                .contentsSize(reviewEventHistory.getContentsSize())
                .photoCount(reviewEventHistory.getPhotoCount())
                .action(reviewEventHistory.getAction())
                .bonusFlag(bonusFlag)
                .build();
        int point = userPointService.addEventApply(userPointAddApplyDto);

        UserMileageDto userMileageDto = userMileageService.updateMileage(userId, point);
        return new ReviewEventResponseDto(eventDto.getUserId(), point, userMileageDto.getMileage());
    }

    private void checkOnlyOneReview(String userId, String placeId) {
        List<ReviewEventHistory> latestReview = reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(userId, placeId);
        if (latestReview.isEmpty()) {
            return;
        }

        ReviewEventHistory reviewEventHistory = latestReview.get(0);
        if (EventAction.DELETE.equals(reviewEventHistory.getAction())) {
            return;
        }
        throw new ServiceException(ResultCodeType.FAIL_R_002);
    }


    public ReviewEventResponseDto onModEvent(EventDto eventDto) {
        log.info("> [Review Mod] eventDto: {}", eventDto);

        String userId = eventDto.getUserId();
        String placeId = eventDto.getPlaceId();

        ReviewEventHistory oldReviewEventHistory = checkValidation(userId, placeId);
        int oldContentsSize = oldReviewEventHistory.getContentsSize();
        int oldPhotoCount = oldReviewEventHistory.getPhotoCount();

        ReviewEventHistory reviewEventHistory = appendReviewEventHistory(new ReviewEventHistory(eventDto));
        int newContentsSize = reviewEventHistory.getContentsSize();
        int newPhotoCount = reviewEventHistory.getPhotoCount();

        UserPointModApplyDto userPointModApplyDto = UserPointModApplyDto.builder()
                .userId(userId)
                .reviewId(reviewEventHistory.getReviewId())
                .action(reviewEventHistory.getAction())
                .oldContentsSize(oldContentsSize)
                .oldPhotoCount(oldPhotoCount)
                .newContentsSize(newContentsSize)
                .newPhotoCount(newPhotoCount)
                .build();
        int point = userPointService.modEventApply(userPointModApplyDto);

        UserMileageDto userMileageDto = userMileageService.updateMileage(userId, point);
        return new ReviewEventResponseDto(eventDto.getUserId(), point, userMileageDto.getMileage());
    }

    private ReviewEventHistory checkValidation(String userId, String placeId) {
        List<ReviewEventHistory> reviewEventHistoryList = reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(userId, placeId);

        if (reviewEventHistoryList.isEmpty()) {
            throw new ServiceException(ResultCodeType.FAIL_R_003);
        }
        ReviewEventHistory oldReviewEventHistory = reviewEventHistoryList.get(0);
        if (EventAction.DELETE.equals(oldReviewEventHistory.getAction())) {
            throw new ServiceException(ResultCodeType.FAIL_R_003);
        }
        return oldReviewEventHistory;
    }

    public ReviewEventResponseDto onDeleteEvent(EventDto eventDto) {
        log.info("> [Review Delete] eventDto: {}", eventDto);

        String userId = eventDto.getUserId();
        String placeId = eventDto.getPlaceId();

        checkValidation(userId, placeId);

        ReviewEventHistory reviewEventHistory = appendReviewEventHistory(new ReviewEventHistory(eventDto));
        UserPointDeleteApplyDto userPointDeleteApplyDto = UserPointDeleteApplyDto.builder()
                .userId(userId)
                .reviewId(reviewEventHistory.getReviewId())
                .action(reviewEventHistory.getAction())
                .build();
        int point = userPointService.deleteEventApply(userPointDeleteApplyDto);

        UserMileageDto userMileageDto = userMileageService.updateMileage(userId, point);
        return new ReviewEventResponseDto(eventDto.getUserId(), point, userMileageDto.getMileage());
    }

    private ReviewEventHistory appendReviewEventHistory(ReviewEventHistory entity) {
        recordPointPolicy(entity);
        return reviewEventHistoryRepository.save(entity);
    }

    private boolean checkFirstReview(String placeId) {
        if (reviewEventHistoryRepository.countByPlaceId(placeId) == 0)
            return true;

        int addCount = reviewEventHistoryRepository.countByPlaceIdAndAction(placeId, EventAction.ADD);
        int deleteCount = reviewEventHistoryRepository.countByPlaceIdAndAction(placeId, EventAction.DELETE);
        return addCount == deleteCount;
    }

    private void recordPointPolicy(ReviewEventHistory reviewEventHistory) {
        reviewEventHistory.recordPointPolicy(userPointService.getPolicy());
    }
}
