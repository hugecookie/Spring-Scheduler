package org.example.service;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto); // ✅ 일정 생성
    Optional<ScheduleResponseDto> getScheduleById(Long id); // ✅ 일정 조회
    List<ScheduleResponseDto> getAllSchedules(); // ✅전체 일정 조회
}
