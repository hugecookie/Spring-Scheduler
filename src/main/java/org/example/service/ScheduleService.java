package org.example.service;

import org.example.dto.ScheduleCreateRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.dto.ScheduleUpdateRequestDto;
import org.example.dto.ScheduleDeleteRequestDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto); // ✅ 일정 생성
    Optional<ScheduleResponseDto> getScheduleById(Long id); // ✅ 일정 조회
    List<ScheduleResponseDto> getAllSchedules(); // ✅ 전체 일정 조회
    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto); // ✅ 일정 수정
    ScheduleResponseDto deleteSchedule(Long id, ScheduleDeleteRequestDto requestDto); // ✅ 일정 삭제
    List<ScheduleResponseDto> getSchedules(int page, int size);
}
