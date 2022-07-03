package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.review.model.entity.UserPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointHistoryRepository extends JpaRepository<UserPointHistory, Long> {
    List<UserPointHistory> findByReviewId(String reviewId);
}
