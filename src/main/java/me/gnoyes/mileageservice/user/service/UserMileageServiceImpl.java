package me.gnoyes.mileageservice.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.type.ResultCodeType;
import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;
import me.gnoyes.mileageservice.user.model.entity.UserMileage;
import me.gnoyes.mileageservice.user.repository.UserMileageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMileageServiceImpl implements UserMileageService {

    private final UserMileageRepository userMileageRepository;

    @Transactional(readOnly = true)
    public UserMileageDto getUserMileage(String userId) {
        Optional<UserMileage> optionalUserMileage = userMileageRepository.findById(userId);
        if (!optionalUserMileage.isPresent()) {
            throw new ServiceException(ResultCodeType.FAIL_U_001);
        }

        return new UserMileageDto(optionalUserMileage.get());
    }

    @Transactional
    public UserMileageDto updateMileage(String userId, int point) {
        Optional<UserMileage> optionalUserMileage = userMileageRepository.findById(userId);

        if (!optionalUserMileage.isPresent()) {
            UserMileage userMileage = creatNewUser(userId, point);
            return new UserMileageDto(userMileage);
        }

        UserMileage userMileage = optionalUserMileage.get();
        userMileage.updateMileage(point);
        return new UserMileageDto(userMileage);
    }

    private UserMileage creatNewUser(String userId, int point) {
        return userMileageRepository.save(new UserMileage(userId, point));
    }
}
