package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewEventHistoryRepository extends JpaRepository<ReviewEventHistory, Long> {
    int countByPlaceId(String placeId);

    int countByPlaceIdAndAction(String placeId, EventAction action);

    List<ReviewEventHistory> findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(String userId, String placeId);
}
