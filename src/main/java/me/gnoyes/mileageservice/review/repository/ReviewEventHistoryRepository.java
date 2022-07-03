package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.review.model.entity.ReviewEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewEventHistoryRepository extends JpaRepository<ReviewEventHistory, Long> {
    Boolean existsByPlaceId(String placeId);
}
