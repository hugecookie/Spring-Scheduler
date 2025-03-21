package org.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

// ✅ Schedule Entity
@Getter
@NoArgsConstructor
public class Schedule {
    @Setter
    private Long id; // 일정 ID (Primary Key)
    private String title; // 일정 제목
    private String description; // 일정 설명
    private String author; // 작성자
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String password; // 일정 비밀번호 (DB 저장, 응답에서는 제외)
    private String status; // 일정 상태 (scheduled, ongoing, completed, canceled)
    private LocalDateTime createdAt; // 생성 날짜
    private LocalDateTime lastUpdatedAt; // 마지막 수정 날짜

    public Schedule(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Schedule(Long id, String title, String description, String author,
                    LocalDate date, LocalTime time, String password,
                    String status, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
        this.time = time;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
