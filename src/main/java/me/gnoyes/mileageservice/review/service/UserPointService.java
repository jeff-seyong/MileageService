package me.gnoyes.mileageservice.review.service;

import me.gnoyes.mileageservice.constants.policy.PointPolicy;
import me.gnoyes.mileageservice.review.model.dto.UserPointAddApplyDto;

public interface UserPointService {

    PointPolicy getPolicy();

    int addEventApply(UserPointAddApplyDto userPointAddApplyDto);
}
