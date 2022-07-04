package me.gnoyes.mileageservice.user.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.review.model.entity.BaseInformation;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_mileage")
@Getter
@NoArgsConstructor
public class UserMileage extends BaseInformation implements Persistable<String> {

    @Id
    @Column(name = "user_id", nullable = false, length = 36, columnDefinition = "리뷰 작성자 아이디")
    private String userId;

    @Column(name = "mileage", nullable = false, columnDefinition = "유저 마일리지")
    private Integer mileage;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserPointHistory> mileageHistory;
    public UserMileage(String userId, Integer mileage) {
        this.userId = userId;
        this.mileage = mileage;
    }

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(super.registerDate);
    }

    public void updateMileage(int point) {
        this.mileage += point;
    }
}
