package me.gnoyes.mileageservice.review.repository;

import me.gnoyes.mileageservice.review.model.entity.UserPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointHistoryRepository extends JpaRepository<UserPointHistory, Long> {
}
