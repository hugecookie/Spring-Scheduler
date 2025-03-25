package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// ✅ 일정 수정 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleUpdateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이내여야 합니다.")
    private String title; // 일정 타이틀

    private String description; // 일정 설명
    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 일정 비밀번호
}