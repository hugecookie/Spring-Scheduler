package org.example.exception;

import org.example.dto.ErrorResponse;
import org.example.entity.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 없는 일정 검색 시 메시지
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(ScheduleNotFoundException ex) {
        return buildErrorResponse(ErrorCode.SCHEDULE_NOT_FOUND);
    }

    // ✅ 비밀번호 불일치 시 메시지
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException ex) {
        return buildErrorResponse(ErrorCode.PASSWORD_MISMATCH);
    }

    // ✅ 알 수 없는 예외 처리 시 메시지
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // ✅ 공통 응답 생성 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

}