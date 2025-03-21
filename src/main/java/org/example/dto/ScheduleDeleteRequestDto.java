package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// ✅ 일정 삭제 요청 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDeleteRequestDto {
//    private Long id;       // ✅ 삭제할 일정의 ID
    private String password; // ✅ 일정 비밀번호 (삭제 시 필요)
}