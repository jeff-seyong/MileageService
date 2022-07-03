package me.gnoyes.mileageservice.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.review.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.review.model.dto.UserPointModApplyDto;
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

    public int addEventApply(UserPointAddApplyDto addDto) {
        log.info("> userPointAddApplyDto: {}", addDto);
        int point = 0;
        if (addDto.isBonusFlag()) {
            point += BONUS_POINT;
        }
        if (addDto.getContentsSize() >= MIN_TEXT_LENGTH) {
            point += TEXT_POINT;
        }
        if (addDto.getPhotoCount() >= MIN_PHOTOS_LENGTH) {
            point += PHOTO_POINT;
        }
        UserPointHistory userPointHistory = new UserPointHistory(addDto.getEventHistoryId(), point);
        UserPointHistory save = userPointHistoryRepository.save(userPointHistory);
        return save.getPoint();
    }

    public int modEventApply(UserPointModApplyDto modDto) {
        log.info("> userPointModApplyDto: {}", modDto);
        int point = 0;

        int oldContentsSize = modDto.getOldContentsSize();
        int newContentsSize = modDto.getNewContentsSize();
        point += checkTextPoint(oldContentsSize, newContentsSize);

        int oldPhotoCount = modDto.getOldPhotoCount();
        int newPhotoCount = modDto.getNewPhotoCount();
        point += checkPhotoPoint(oldPhotoCount, newPhotoCount);

        UserPointHistory userPointHistory = new UserPointHistory(modDto.getEventHistoryId(), point);
        UserPointHistory save = userPointHistoryRepository.save(userPointHistory);
        return save.getPoint();
    }

    private int checkTextPoint(int oldContentsSize, int newContentsSize) {
        if (oldContentsSize == newContentsSize) {
            return 0;
        }
        // 리뷰 내용 길이가 이전과 다른 경우
        // 수정된 길이가 MIN_TEXT_LENGTH 보다 작으면 : 포인트 회수
        // 수정된 길이가 MIN_TEXT_LENGTH 보다 크거나 같으면 : 포인트 부여
        return newContentsSize < MIN_TEXT_LENGTH ? -TEXT_POINT : TEXT_POINT;
    }

    private int checkPhotoPoint(int oldPhotoCount, int newPhotoCount) {
        if (oldPhotoCount == newPhotoCount) {
            return 0;
        }
        // 첨부 이미지 개수가 이전과 다르고,
        // 수정된 길이가 MIN_PHOTOS_LENGTH 보다 작으면 >> 포인트 회수
        // 수정된 길이가 MIN_PHOTOS_LENGTH 보다 크거나 같으면 >> 포인트 부여
        return newPhotoCount < MIN_PHOTOS_LENGTH ? -PHOTO_POINT : PHOTO_POINT;
    }
}