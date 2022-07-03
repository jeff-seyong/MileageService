package me.gnoyes.mileageservice.exception;

import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 공통 API 익셉션 핸들러
 */
@Slf4j
public class ApiExceptionHandler {

//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public ObjectMapper getObjectMapper() {
//        return this.objectMapper;
//    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
        log.error(req.getRequestURL() + " NoHandlerFoundException: {}", e.getMessage());

        CommonResponse commonResponse = new CommonResponse(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity invalidFormatException(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error(req.getRequestURL() + " HttpMessageNotReadableException: {}", e.getMessage());

        CommonResponse commonResponse = new CommonResponse(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity validException(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error(req.getRequestURL() + " MethodArgumentNotValidException: {}", e.getMessage());

        CommonResponse commonResponse = new CommonResponse(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({Exception.class})
    public ResponseEntity exception(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL() + " Excetion", e);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
    }
}
