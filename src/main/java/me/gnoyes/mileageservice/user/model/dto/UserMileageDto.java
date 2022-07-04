package me.gnoyes.mileageservice.user.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.user.model.entity.UserMileage;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserMileageDto {

    private String userId;

    private Integer mileage;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    public UserMileageDto(UserMileage entity) {
        this.userId = entity.getUserId();
        this.mileage = entity.getMileage();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
    }
}

