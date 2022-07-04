package me.gnoyes.mileageservice.user.repository;

import me.gnoyes.mileageservice.user.model.entity.UserMileage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMileageRepository extends JpaRepository<UserMileage, String> {
}
