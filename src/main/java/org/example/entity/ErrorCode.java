package org.example.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SCHEDULE_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(1002, HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    VALIDATION_ERROR(1003, HttpStatus.BAD_REQUEST, "요청 데이터가 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR(1999, HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류가 발생했습니다.");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}

