package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.review.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.review.model.entity.UserPointHistory;
import me.gnoyes.mileageservice.review.repository.UserPointHistoryRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StandardUserPointService implements UserPointService {

    private final int MIN_TEXT_LENGTH = 1;
    private final int MIN_PHOTOS_LENGTH = 1;
    private final int BONUS_POINT = 1;
    private final int TEXT_POINT = 1;
    private final int PHOTO_POINT = 1;

    private final UserPointHistoryRepository userPointHistoryRepository;

    public PointPolicy getPolicy() {
        return PointPolicy.STANDARD;
    }

    public int addEventApply(UserPointAddApplyDto userPointAddApplyDto) {
        log.info("> userPointAddApplyDto: {}", userPointAddApplyDto);
        int point = 0;
        if (userPointAddApplyDto.isBonusFlag()) {
            point += BONUS_POINT;
        }
        if (userPointAddApplyDto.getContentsSize() >= MIN_TEXT_LENGTH) {
            point += TEXT_POINT;
        }
        if (userPointAddApplyDto.getPhotoCount() >= MIN_PHOTOS_LENGTH) {
            point += PHOTO_POINT;
        }
        UserPointHistory userPointHistory = new UserPointHistory(userPointAddApplyDto.getEventHistoryId(), point);
        UserPointHistory save = userPointHistoryRepository.save(userPointHistory);
        return save.getPoint();
    }

}
