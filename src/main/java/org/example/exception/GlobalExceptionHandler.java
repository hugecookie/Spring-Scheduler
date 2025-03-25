package org.example.exception;

import org.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 없는 일정 검색 시 메시지
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(ScheduleNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "SCHEDULE_NOT_FOUND", ex.getMessage());
    }

    // ✅ 비밀번호 불일치 시 메시지
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "PASSWORD_MISMATCH", ex.getMessage());
    }

    // ✅ 알 수 없는 예외 처리 시 메시지
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "알 수 없는 서버 오류가 발생했습니다.");
    }

    // ✅ 공통 응답 생성 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String code, String message) {
        ErrorResponse response = new ErrorResponse(code, message, status.value());
        return ResponseEntity.status(status).body(response);
    }

}