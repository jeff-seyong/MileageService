package me.gnoyes.mileageservice.user.service;

import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;
import me.gnoyes.mileageservice.user.model.dto.UserMileageResponseDto;

public interface UserMileageService {

    UserMileageResponseDto getUserMileage(String userId);

    UserMileageDto updateMileage(String userId, int point);
}
