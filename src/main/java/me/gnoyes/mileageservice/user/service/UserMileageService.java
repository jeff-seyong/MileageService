package me.gnoyes.mileageservice.user.service;

import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;

public interface UserMileageService {

    UserMileageDto getUserMileage(String userId);

    UserMileageDto updateMileage(String userId, int point);
}
