package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// ✅ 일정 수정 요청 DTO
@Getter
@NoArgsConstructor
public class ScheduleUpdateRequestDto {
    private String title;  // 수정할 일정 제목
    private String author; // 수정할 작성자명
    private String password; // 일정 비밀번호 (수정 시 필요)
}
