package com.liumulin.webflux.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liuqiang
 * @since 2021-09-07
 */
@Getter
@Setter
public class StudentException extends RuntimeException {
    private String errorField;

    private String errorValue;

    public StudentException() {
        super();
    }

    public StudentException(String errorField, String errorValue, String message) {
        super(message);
        this.errorField = errorField;
        this.errorValue = errorValue;
    }
}
