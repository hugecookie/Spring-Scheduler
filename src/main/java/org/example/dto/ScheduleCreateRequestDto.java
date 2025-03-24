package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// ✅ 일정 생성 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleCreateRequestDto {
    private String title; // 일정 제목
    private String description; // 일정 설명 (선택 사항)
    private AuthorDto author; // 작성자 Dto
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간
    private String password; // 일정 비밀번호 (수정/삭제 시 필요, 응답에는 포함 X)
}
