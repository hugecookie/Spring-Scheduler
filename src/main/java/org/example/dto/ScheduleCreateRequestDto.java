package org.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// ✅ 일정 생성 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleCreateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, min = 1, message = "0~200자 사이로 작성해주십시오.")
    private String title; // 일정 제목

    private String description; // 일정 설명 (선택 사항)

    @Valid
    @NotNull(message = "작성자 정보는 필수입니다.")
    private AuthorDto author; // 작성자 Dto

    private LocalDate date; // 일정 날짜
    private LocalTime time; // 일정 시간

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 일정 비밀번호 (수정/삭제 시 필요, 응답에는 포함 X)
}
