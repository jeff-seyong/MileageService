package me.gnoyes.mileageservice.review.service;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.constants.type.EventType;
import me.gnoyes.mileageservice.event.model.dto.EventDto;
import me.gnoyes.mileageservice.event.model.dto.EventDtoBuilder;
import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.user.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.user.service.UserPointService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ReviewServiceImplRollbackTest {

    @Autowired
    ReviewService reviewService;
    @MockBean
    UserPointService userPointService;

    // @Test
    @Transactional
    @Rollback(value = false)
    void 자식_트랜잭션에서_익셉션_발생하는_경우() {
        // given
        String userId = "userId-1";
        String placeId = "placeId-1";
        String reviewId = "reviewId-1";
        String content = "너무 좋은것 같아요!";
        List<String> photoIds = Arrays.asList("photoId-1", "photoId-2");
        EventDto eventDto = EventDtoBuilder.of(EventType.REVIEW, EventAction.ADD, reviewId, content, photoIds, userId, placeId);

        given(userPointService.getPolicy())
                .willReturn(PointPolicy.STANDARD);

        given(userPointService.addEventApply(any(UserPointAddApplyDto.class)))
                .willThrow(ServiceException.class);


        // assertThrows(ServiceException.class, ()-> reviewService.distribute(eventDto));
        // 롤백은 정상 동작. 그러나 UnexpectedRollbackException 핸들링 되지 않음.
    }
}
