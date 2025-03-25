package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

// ✅ 일정 삭제 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleDeleteRequestDto {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 일정 비밀번호 (삭제 시 필요)
}