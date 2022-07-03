package me.gnoyes.mileageservice.review.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseInformation {

    @Column(name = "register_date", updatable = false, nullable = false)
    @CreatedDate
    protected LocalDateTime registerDate;

    @Column(name = "update_date", nullable = true)
    @LastModifiedDate
    protected LocalDateTime updateDate;

    protected void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    protected void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
