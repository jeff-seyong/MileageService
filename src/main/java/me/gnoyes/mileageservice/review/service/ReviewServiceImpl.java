package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.review.model.dto.ReviewEventResponseDto;
import me.gnoyes.mileageservice.review.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import me.gnoyes.mileageservice.review.repository.ReviewEventHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewEventHistoryRepository reviewEventHistoryRepository;
    private final UserPointService userPointService;

    public ReviewEventResponseDto distribute(EventDto eventDto) {
        EventAction action = eventDto.getAction();
        switch (action) {
            case ADD:
                return onAddEvent(eventDto);
            case MOD:
                onModEvent(eventDto);
                return new ReviewEventResponseDto();
            case DELETE:
                onDeleteEvent(eventDto);
                return new ReviewEventResponseDto();
            default:
                throw new RuntimeException();
        }
    }

    @Transactional
    public ReviewEventResponseDto onAddEvent(EventDto eventDto) {
        log.info("> [Review Add] eventDto: {}", eventDto);
        boolean bonusFlag = checkFirstReview(eventDto.getPlaceId());
        log.info("> userId: {}, placeId: {}, bonusFlag: {}", eventDto.getUserId(), eventDto.getPlaceId(), bonusFlag);
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

    public void onModEvent(EventDto eventDto) {
        log.info("ReviewServiceImpl.onModEvent");
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
