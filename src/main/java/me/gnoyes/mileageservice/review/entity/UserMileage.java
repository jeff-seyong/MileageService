package me.gnoyes.mileageservice.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(super.registerDate);
    }
}
