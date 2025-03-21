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
    private String title;
    private String description;
    private String author;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
