package me.gnoyes.mileageservice.user.service;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.type.PointType;
import me.gnoyes.mileageservice.user.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointDeleteApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointModApplyDto;
import me.gnoyes.mileageservice.user.model.entity.UserPointHistory;
import me.gnoyes.mileageservice.user.model.entity.UserPointHistoryBuilder;
import me.gnoyes.mileageservice.user.repository.UserPointHistoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class StandardUserPointServiceTest {

    @InjectMocks
    StandardUserPointService userPointService;

    @Mock
    UserPointHistoryRepository userPointHistoryRepository;

    @Test
    @Order(1)
    void 리뷰등록_내용X_첨부이미지X_장소첫리뷰X_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(0)
                .photoCount(0)
                .action(EventAction.ADD)
                .bonusFlag(false)
                .build();

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(0);
    }

    @Test
    @Order(2)
    void 리뷰등록_내용O_첨부이미지X_장소첫리뷰X_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(1)
                .photoCount(0)
                .action(EventAction.ADD)
                .bonusFlag(false)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(1);
    }

    @Test
    @Order(3)
    void 리뷰등록_내용X_첨부이미지O_장소첫리뷰X_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(0)
                .photoCount(1)
                .action(EventAction.ADD)
                .bonusFlag(false)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(1);
    }

    @Test
    @Order(4)
    void 리뷰등록_내용X_첨부이미지X_장소첫리뷰O_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(0)
                .photoCount(0)
                .action(EventAction.ADD)
                .bonusFlag(true)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(1);
    }


    @Test
    @Order(5)
    void 리뷰등록_내용O_첨부이미지O_장소첫리뷰X_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(1)
                .photoCount(1)
                .action(EventAction.ADD)
                .bonusFlag(false)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(2);
    }

    @Test
    @Order(6)
    void 리뷰등록_내용O_첨부이미지O_장소첫리뷰O_경우() {
        // given
        UserPointAddApplyDto addApplyDto = UserPointAddApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .contentsSize(1)
                .photoCount(1)
                .action(EventAction.ADD)
                .bonusFlag(true)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.addEventApply(addApplyDto);

        // then
        assertThat(point).isEqualTo(3);
    }

    @Test
    @Order(7)
    void 리뷰수정_내용_수정하여_내용포인트_받게되는_경우() {
        // given
        UserPointModApplyDto modApplyDto = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(0)
                .oldPhotoCount(0)
                .newContentsSize(10)
                .newPhotoCount(0)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.modEventApply(modApplyDto);

        // then
        assertThat(point).isEqualTo(1);
    }

    @Test
    @Order(8)
    void 리뷰수정_이미_내용포인트_받은_유저가_수정하는_경우() {
        // given
        // 수정한 리뷰도 내용 포인트를 받을 자격이 있는 경우 => 그대로
        UserPointModApplyDto modApplyDto1 = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(10)
                .oldPhotoCount(0)
                .newContentsSize(20)
                .newPhotoCount(0)
                .build();
        // 수정한 리뷰가 내용 포인트를 받을 자격이 없는 경우 => 받은 내용 포인트 회수
        UserPointModApplyDto modApplyDto2 = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(10)
                .oldPhotoCount(0)
                .newContentsSize(0)
                .newPhotoCount(0)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point1 = userPointService.modEventApply(modApplyDto1);
        int point2 = userPointService.modEventApply(modApplyDto2);

        // then
        assertThat(point1).isEqualTo(0);
        assertThat(point2).isEqualTo(-1);
    }

    @Test
    @Order(9)
    void 리뷰수정_내용_수정하여_포토포인트_받게되는_경우() {
        // given
        UserPointModApplyDto modApplyDto = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(0)
                .oldPhotoCount(0)
                .newContentsSize(10)
                .newPhotoCount(0)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.modEventApply(modApplyDto);

        // then
        assertThat(point).isEqualTo(1);
    }

    @Test
    @Order(10)
    void 리뷰수정_이미_포토포인트_받은_유저가_수정하는_경우() {
        // given
        // 수정한 리뷰도 포토 포인트를 받을 자격이 있는 경우 => 그대로
        UserPointModApplyDto modApplyDto1 = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(0)
                .oldPhotoCount(2)
                .newContentsSize(0)
                .newPhotoCount(3)
                .build();
        // 수정한 리뷰가 포토 포인트를 받을 자격이 없는 경우 => 받은 포토 포인트 회수
        UserPointModApplyDto modApplyDto2 = UserPointModApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.MOD)
                .oldContentsSize(0)
                .oldPhotoCount(2)
                .newContentsSize(0)
                .newPhotoCount(0)
                .build();
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point1 = userPointService.modEventApply(modApplyDto1);
        int point2 = userPointService.modEventApply(modApplyDto2);

        // then
        assertThat(point1).isEqualTo(0);
        assertThat(point2).isEqualTo(-1);
    }

    @Test
    @Order(11)
    void 리뷰삭제_리뷰가_삭제되면_받았던_포인트들_회수() {
        // given
        UserPointDeleteApplyDto deleteApplyDto = UserPointDeleteApplyDto.builder()
                .userId("userId-1")
                .reviewId("reviewId-1")
                .action(EventAction.DELETE)
                .build();

        List<UserPointHistory> userPointHistoryList = getDummyUserPointHistory();
        given(userPointHistoryRepository.findByReviewId(any(String.class)))
                .willReturn(userPointHistoryList);
        given(userPointHistoryRepository.save(any(UserPointHistory.class)))
                .willReturn(new UserPointHistory());

        // when
        int point = userPointService.deleteEventApply(deleteApplyDto);

        assertThat(point).isEqualTo(-3);
    }

    private List<UserPointHistory> getDummyUserPointHistory() {
        List<UserPointHistory> dummy = new ArrayList<>();
        dummy.add(UserPointHistoryBuilder.of(null, "userId-1", "reviewId-1", 1, PointType.TEXT_POINT));
        dummy.add(UserPointHistoryBuilder.of(null, "userId-1", "reviewId-1", 1, PointType.PHOTO_POINT));
        dummy.add(UserPointHistoryBuilder.of(null, "userId-1", "reviewId-1", 1, PointType.BONUS_POINT));
        return dummy;
    }
}