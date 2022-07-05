package me.gnoyes.mileageservice.user.service;

import me.gnoyes.mileageservice.exception.ServiceException;
import me.gnoyes.mileageservice.user.model.dto.UserMileageDto;
import me.gnoyes.mileageservice.user.model.dto.UserMileageResponseDto;
import me.gnoyes.mileageservice.user.model.entity.UserMileage;
import me.gnoyes.mileageservice.user.model.entity.UserMileageBuilder;
import me.gnoyes.mileageservice.user.model.entity.UserPointHistory;
import me.gnoyes.mileageservice.user.repository.UserMileageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserMileageServiceImplTest {

    @InjectMocks
    UserMileageServiceImpl userMileageService;

    @Mock
    UserMileageRepository userMileageRepository;

    @Test
    void 유저마일리지_조회_정상() {
        // given
        String userId = "userId-1";
        int mileage = 100;
        List<UserPointHistory> mileageHistory = new ArrayList<>();

        Optional<UserMileage> optionalUserMileage = Optional.of(
                UserMileageBuilder.of(userId, mileage, mileageHistory, now(), now())
        );
        given(userMileageRepository.findById(any(String.class)))
                .willReturn(optionalUserMileage);

        // when
        UserMileageResponseDto responseDto = userMileageService.getUserMileage(userId);

        // then
        assertThat(responseDto.getUserId()).isEqualTo(userId);
        assertThat(responseDto.getMileage()).isEqualTo(mileage);
    }

    @Test
    void 유저마일리지_조회_존재하지_않는_유저() {
        // given
        String userId = "wrongUserId-1";
        Optional<UserMileage> optionalUserMileage = Optional.empty();
        given(userMileageRepository.findById(any(String.class)))
                .willReturn(optionalUserMileage);

        // then
        assertThrows(ServiceException.class, () -> userMileageService.getUserMileage(userId));
    }

    @Test
    void 유저마일리지_업데이트_정상() {
        // given
        String userId = "userId-1";
        int point = 3;
        int mileage = 100;
        int expectMileage = mileage + point;
        List<UserPointHistory> mileageHistory = new ArrayList<>();

        Optional<UserMileage> optionalUserMileage = Optional.of(
                UserMileageBuilder.of(userId, mileage, mileageHistory, now(), now())
        );
        given(userMileageRepository.findById(any(String.class)))
                .willReturn(optionalUserMileage);

        // when
        UserMileageDto userMileageDto = userMileageService.updateMileage(userId, point);

        // then
        assertThat(userMileageDto.getUserId()).isEqualTo(userId);
        assertThat(userMileageDto.getMileage()).isEqualTo(expectMileage);
    }

    @Test
    void 유저마일리지_업데이트_존재하지_않는_유저() {
        // given
        String userId = "wrongUserId-1";
        int point = 3;
        int mileage = 3;
        int expectMileage = 3;
        Optional<UserMileage> optionalUserMileage = Optional.empty();
        given(userMileageRepository.findById(any(String.class)))
                .willReturn(optionalUserMileage);
        UserMileage userMileage = UserMileageBuilder.of(userId, mileage, new ArrayList<>(), now(), now());
        given(userMileageRepository.save(any(UserMileage.class)))
                .willReturn(userMileage);

        // when
        UserMileageDto userMileageDto = userMileageService.updateMileage(userId, point);

        // then
        assertThat(userMileageDto.getUserId()).isEqualTo(userId);
        assertThat(userMileageDto.getMileage()).isEqualTo(expectMileage);
    }
}