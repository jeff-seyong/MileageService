package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewEventHistoryRepositoryTest {
    @Autowired
    ReviewEventHistoryRepository repository;

    private List<Long> willDeleteAfterTest = new ArrayList<>();
    @BeforeAll
    void init() {
        List<ReviewEventHistory> data = prepareData();
        List<ReviewEventHistory> saveAll = repository.saveAll(data);
        this.willDeleteAfterTest.addAll(saveAll.stream().map(ReviewEventHistory::getEventHistoryId).collect(Collectors.toList()));
    }

    @AfterAll
    void deleteTestData() {
        repository.deleteAllById(this.willDeleteAfterTest);
    }

    @Test
    void countByPlaceId() {
        int count = repository.countByPlaceId("placeId-1");
        assertThat(count).isEqualTo(4);
    }

    @Test
    void countByPlaceIdAndAction() {
        int count = repository.countByPlaceIdAndAction("placeId-1", EventAction.ADD);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void findTopByUserIdAndPlaceIdOrderByRegisterDateDesc() {
        List<ReviewEventHistory> latestReviewEvent = repository.findTopByUserIdAndPlaceIdOrderByRegisterDateDesc("userId-1", "placeId-1");

        assertThat(latestReviewEvent.isEmpty()).isFalse();
        assertThat(latestReviewEvent.size()).isEqualTo(1);

        ReviewEventHistory reviewEventHistory = latestReviewEvent.get(0);
        assertThat(reviewEventHistory.getAction()).isEqualTo(EventAction.MOD);
        assertThat(reviewEventHistory.getContentsSize()).isEqualTo(20);
        assertThat(reviewEventHistory.getPhotoCount()).isEqualTo(10);
    }

    private static List<ReviewEventHistory> prepareData() {
        List<ReviewEventHistory> data = new ArrayList<>();
        // placeId-1
        data.add(ReviewEventHistoryBuilder.of("userId-1", "placeId-1", "reviewId-1", EventAction.ADD, 5, 5, PointPolicy.STANDARD, LocalDateTime.now(), null));
        data.add(ReviewEventHistoryBuilder.of("userId-2", "placeId-1", "reviewId-2", EventAction.ADD, 10, 0, PointPolicy.STANDARD, LocalDateTime.now(), null));
        data.add(ReviewEventHistoryBuilder.of("userId-1", "placeId-1", "reviewId-1", EventAction.MOD, 10, 5, PointPolicy.STANDARD, LocalDateTime.now().plusSeconds(10), null));
        data.add(ReviewEventHistoryBuilder.of("userId-1", "placeId-1", "reviewId-1", EventAction.MOD, 20, 10, PointPolicy.STANDARD, LocalDateTime.now().plusSeconds(20), null));
        // placeId-2
        data.add(ReviewEventHistoryBuilder.of("userId-1", "placeId-2", "reviewId-3", EventAction.ADD, 2, 2, PointPolicy.STANDARD, LocalDateTime.now().plusMinutes(10), null));

        // countByPlaceId
        // > expect 4
        // countByPlaceIdAndAction
        // > expect 2;
        // findTopByUserIdAndPlaceIdOrderByRegisterDateDesc
        // > expect ("userId-1", "placeId-1", "reviewId-1", EventAction.MOD, 20, 10, PointPolicy.STANDARD, LocalDateTime.now().plusSeconds(20), null)
        return data;
    }

}