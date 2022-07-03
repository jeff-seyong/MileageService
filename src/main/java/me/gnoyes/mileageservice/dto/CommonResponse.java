package me.gnoyes.mileageservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 8088971173865321662L;

    private final String code;

    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;

    public CommonResponse() {
        this.code = "200";
        this.message = "success";
        this.timestamp = LocalDateTime.now();
    }

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public CommonResponse(HttpStatus httpStatus) {
        this(String.valueOf(httpStatus.value()), httpStatus.getReasonPhrase());
    }

    public CommonResponse(T result) {
        this();
        this.result = result;
    }
}
