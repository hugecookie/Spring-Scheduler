package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

// ✅ 일정 조회 응답 DTO
@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    // ✅ 생성자
    public ScheduleResponseDto(Long id, String title, String description, String author,
                               LocalDate date, LocalTime time, String status,
                               LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
        this.time = time;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
