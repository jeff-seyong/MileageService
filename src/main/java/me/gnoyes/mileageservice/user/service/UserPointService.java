package me.gnoyes.mileageservice.user.service;

import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.user.model.dto.UserPointAddApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointDeleteApplyDto;
import me.gnoyes.mileageservice.user.model.dto.UserPointModApplyDto;

public interface UserPointService {

    PointPolicy getPolicy();

    int addEventApply(UserPointAddApplyDto userPointAddApplyDto);

    int modEventApply(UserPointModApplyDto modDto);

    int deleteEventApply(UserPointDeleteApplyDto deleteDto);
}
