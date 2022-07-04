package me.gnoyes.mileageservice.user.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gnoyes.mileageservice.user.model.entity.UserMileage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserMileageResponseDto {

    private String userId;
    private Integer mileage;
    private List<UserPointHistoryDto> history;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public UserMileageResponseDto(UserMileage entity) {
        this.userId = entity.getUserId();
        this.mileage = entity.getMileage();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
        this.history = entity.getMileageHistory().stream()
                .map(UserPointHistoryDto::new)
                .sorted(Comparator.comparing(UserPointHistoryDto::getRegisterDate).reversed())
                .collect(Collectors.toList());
    }
}

