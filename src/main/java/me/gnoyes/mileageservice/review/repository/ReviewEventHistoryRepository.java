package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.constants.action.EventAction;
import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewEventHistoryRepository extends JpaRepository<ReviewEventHistory, Long> {
    Boolean existsByPlaceId(String placeId);

    Optional<ReviewEventHistory> findByUserIdAndPlaceIdAndAction(String userId, String placeId, EventAction action);
    List<ReviewEventHistory> findTopByUserIdAndPlaceIdOrderByRegisterDateDesc(String userId, String placeId);
}
