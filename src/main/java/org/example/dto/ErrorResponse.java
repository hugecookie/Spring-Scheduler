package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.ErrorCode;

// ✅ 에러 메세지 Dto
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String code;

    @Setter
    private String message;

    private final int status;

    // ErrorCode 기반 생성자
    public ErrorResponse(ErrorCode errorCode) {
        this.code = String.valueOf(errorCode.getCode());
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
    }

}

