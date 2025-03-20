package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

// ✅ 일정 조회 응답 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id; // 일정 ID
    private String title; // 일정 제목
    private String description; // 일정 설명
    private String author; // 작성자 명
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String status; // 일정 상태 (scheduled, ongoing, completed, canceled)
    private LocalDateTime createdAt; // 생성 날짜
    private LocalDateTime lastUpdatedAt; // 마지막 수정 날짜
}
