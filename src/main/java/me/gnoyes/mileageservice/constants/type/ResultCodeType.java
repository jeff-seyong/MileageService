package me.gnoyes.mileageservice.constants.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCodeType {
    SUCCESS(HttpStatus.OK, "S200", "성공"),

    FAIL_G_000(HttpStatus.INTERNAL_SERVER_ERROR, "G000", "서버 내부 오류"),
    FAIL_G_001(HttpStatus.BAD_REQUEST, "G001", "잘못된 요청입니다"),


    FAIL_E_001(HttpStatus.BAD_REQUEST, "E001", "정의되지 않은 이벤트 타입입니다"),

    FAIL_R_001(HttpStatus.BAD_REQUEST, "R001", "정의되지 않은 액션 타입입니다"),
    FAIL_R_002(HttpStatus.BAD_REQUEST, "R002", "이미 등록한 리뷰가 있습니다"),
    FAIL_R_003(HttpStatus.BAD_REQUEST, "R003", "리뷰가 존재하지 않거나 삭제된 리뷰입니다"),
    ;

    private HttpStatus httpStatus;
    private String code;
    private String desc;

    ResultCodeType(HttpStatus httpStatus, String code, String desc) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.desc = desc;
    }

}

