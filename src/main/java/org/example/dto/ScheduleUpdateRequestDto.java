package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// ✅ 일정 수정 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleUpdateRequestDto {
    private String title; // 일정 타이틀
    private String description; // 일정 설명
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String password; // 일정 비밀번호
}