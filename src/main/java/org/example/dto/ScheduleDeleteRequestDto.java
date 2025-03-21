package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// ✅ 일정 삭제 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleDeleteRequestDto {
    private String password; // 일정 비밀번호 (삭제 시 필요)
}