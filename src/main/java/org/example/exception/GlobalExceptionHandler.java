package org.example.exception;

import org.example.dto.ErrorResponse;
import org.example.entity.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

    /* ✅ 유효성 검사 실패
     * - 이 경우에는 입력 값 검사에서 실패한 경우이므로 메시지 내용을 커스텀해서 반환하는 형식으로 구성
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        // ✅ 지시한 내용대로 입력하지 않는 오류 메시지 출력
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse(ErrorCode.VALIDATION_ERROR);
        response.setMessage(errorMessage);

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus()).body(response);
    }

    // ✅ 공통 응답 생성 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

}