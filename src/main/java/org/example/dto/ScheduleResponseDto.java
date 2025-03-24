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
    private Long id; // 일정 고유 id
    private String title; // 일정 타이틀
    private String description; // 상세설명
    private String authorName; // 작성자 이름
    private String authorEmail; // 작성자 이메일
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String status; // 일정 상태
    private LocalDateTime createdAt; // 생성된 시간
    private LocalDateTime lastUpdatedAt; // 마지막으로 수정된 시간

    // ✅ 생성자
    public ScheduleResponseDto(Long id, String title, String description,
                               String authorName, String authorEmail,
                               LocalDate date, LocalTime time, String status,
                               LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.date = date;
        this.time = time;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
