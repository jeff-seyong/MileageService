package me.gnoyes.mileageservice.exception;

import lombok.extern.slf4j.Slf4j;
import me.gnoyes.mileageservice.constants.type.ResultCodeType;
import me.gnoyes.mileageservice.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 공통 API 익셉션 핸들러
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL() + " Exception", e);
        ResultCodeType resultCode = ResultCodeType.FAIL_G_000;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
        log.error(req.getRequestURL() + " NoHandlerFoundException: {}", e.getMessage());
        ResultCodeType resultCode = ResultCodeType.FAIL_G_001;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity invalidFormatException(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error(req.getRequestURL() + " HttpMessageNotReadableException: {}", e.getMessage());
        ResultCodeType resultCode = ResultCodeType.FAIL_G_001;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error(req.getRequestURL() + " MethodArgumentNotValidException: {}", e.getMessage());
        ResultCodeType resultCode = ResultCodeType.FAIL_G_001;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        log.error(req.getRequestURL() + " HttpRequestMethodNotSupportedException: {}", e.getMessage());
        ResultCodeType resultCode = ResultCodeType.FAIL_G_001;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity handleMethodArgumentTypeMismatchException(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        log.error(req.getRequestURL() + " MethodArgumentTypeMismatchException: {}", e.getMessage());
        ResultCodeType resultCode = ResultCodeType.FAIL_G_001;

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity handleServiceException(HttpServletRequest req, ServiceException e) {
        log.error(req.getRequestURL() + " serviceException", e);
        ResultCodeType resultCode = e.getResultCode();

        CommonResponse commonResponse = new CommonResponse(resultCode.getCode(), resultCode.getDesc());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(commonResponse);
    }
}
