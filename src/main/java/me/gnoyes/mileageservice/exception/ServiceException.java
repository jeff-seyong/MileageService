package me.gnoyes.mileageservice.exception;

import lombok.Getter;
import me.gnoyes.mileageservice.constants.type.ResultCodeType;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -4689767628655569443L;

    @Getter
    private final ResultCodeType resultCode;

    public ServiceException(ResultCodeType resultCode, Throwable e) {
        super(e);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCodeType resultCode) {
        this.resultCode = resultCode;
    }
}
