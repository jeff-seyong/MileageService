package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;
import me.gnoyes.mileageservice.review.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.review.model.dto.UserPointModApplyDto;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import me.gnoyes.mileageservice.review.repository.ReviewEventHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewEventHistoryRepository reviewEventHistoryRepository;
    private final UserPointService userPointService;

    @Transactional
    public ReviewEventResponseDto distribute(EventDto eventDto) {
        EventAction action = eventDto.getAction();
        switch (action) {
            case ADD:
                return onAddEvent(eventDto);
            case MOD:
                return onModEvent(eventDto);
            case DELETE:
                onDeleteEvent(eventDto);
                return new ReviewEventResponseDto();
            default:
                throw new RuntimeException();
        }
    }

    public ReviewEventResponseDto onAddEvent(EventDto eventDto) {
        log.info("> [Review Add] eventDto: {}", eventDto);

        String userId = eventDto.getUserId();
        String placeId = eventDto.getPlaceId();
        Optional<ReviewEventHistory> optionalReviewEventHistory = reviewEventHistoryRepository.findByUserIdAndPlaceIdAndAction(userId, placeId, EventAction.ADD);

        if (optionalReviewEventHistory.isPresent()) {
            throw new RuntimeException();
        }

        boolean bonusFlag = checkFirstReview(placeId);
        log.info("> userId: {}, placeId: {}, bonusFlag: {}", userId, placeId, bonusFlag);
        ReviewEventHistory reviewEventHistory = addReviewEventHistory(new ReviewEventHistory(eventDto));

        UserPointAddApplyDto userPointAddApplyDto = UserPointAddApplyDto.builder()
                .eventHistoryId(reviewEventHistory.getEventHistoryId())
                .contentsSize(reviewEventHistory.getContentsSize())
                .photoCount(reviewEventHistory.getPhotoCount())
                .action(reviewEventHistory.getAction())
                .bonusFlag(bonusFlag)
                .build();
        int point = userPointService.addEventApply(userPointAddApplyDto);

        return new ReviewEventResponseDto(eventDto.getUserId(), point);
    }


    public ReviewEventResponseDto onModEvent(EventDto eventDto) {
        log.info("> [Review Mod] eventDto: {}", eventDto);

        String userId = eventDto.getUserId();
        String placeId = eventDto.getPlaceId();
        List<ReviewEventHistory> reviewEventHistoryList = reviewEventHistoryRepository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(userId, placeId);

        if (reviewEventHistoryList.isEmpty()) {
            throw new RuntimeException();
        }
        ReviewEventHistory oldReviewEventHistory = reviewEventHistoryList.get(0);
        if (EventAction.DELETE.equals(oldReviewEventHistory.getAction())) {
            throw new RuntimeException();
        }

        int oldContentsSize = oldReviewEventHistory.getContentsSize();
        int oldPhotoCount = oldReviewEventHistory.getPhotoCount();

        ReviewEventHistory reviewEventHistory = addReviewEventHistory(new ReviewEventHistory(eventDto));
        int newContentsSize = reviewEventHistory.getContentsSize();
        int newPhotoCount = reviewEventHistory.getPhotoCount();

        UserPointModApplyDto userPointModApplyDto = UserPointModApplyDto.builder()
                .eventHistoryId(reviewEventHistory.getEventHistoryId())
                .action(reviewEventHistory.getAction())
                .oldContentsSize(oldContentsSize)
                .oldPhotoCount(oldPhotoCount)
                .newContentsSize(newContentsSize)
                .newPhotoCount(newPhotoCount)
                .build();
        int point = userPointService.modEventApply(userPointModApplyDto);

        return new ReviewEventResponseDto(eventDto.getUserId(), point);
    }

    public void onDeleteEvent(EventDto eventDto) {
        log.info("ReviewServiceImpl.onDeleteEvent");
    }

    private ReviewEventHistory addReviewEventHistory(ReviewEventHistory entity) {
        recordPointPolicy(entity);
        return reviewEventHistoryRepository.save(entity);
    }

    private boolean checkFirstReview(String placeId) {
        return !reviewEventHistoryRepository.existsByPlaceId(placeId);
    }

    private void recordPointPolicy(ReviewEventHistory reviewEventHistory) {
        reviewEventHistory.recordPointPolicy(userPointService.getPolicy());
    }
}
