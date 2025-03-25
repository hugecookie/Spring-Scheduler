package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// ✅ 에러 메세지 Dto
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private final int status;
}

