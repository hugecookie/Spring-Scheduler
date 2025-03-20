package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

// ✅ 일정 생성/수정 요청 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String title; // 일정 제목
    private String description; // 일정 설명 (선택 사항)
    private String author; // 작성자 명
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String password; // 일정 비밀번호 (수정/삭제 시 필요, 응답에는 포함 X)
}
