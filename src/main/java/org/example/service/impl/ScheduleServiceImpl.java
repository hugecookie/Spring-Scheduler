package org.example.service.impl;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.repository.ScheduleRepository;
import org.example.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // ✅ [일정 생성] 새로운 일정 저장
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                null, // ID는 자동 생성
                requestDto.getTitle(),
                requestDto.getAuthor(),
                requestDto.getDescription(),
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getPassword(),
                "scheduled",
                LocalDateTime.now(),
                null
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToResponseDto(savedSchedule);
    }

    // ✅ [특정 일정 조회] {id} 값에 맞춰서 해당 일정을 조회
    @Override
    public Optional<ScheduleResponseDto> getScheduleById(Long id) {
        return scheduleRepository.findById(id).map(this::convertToResponseDto);
    }

    // ✅ [일정 조회] 모든 일정 조회
    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // ✅ Schedule 엔티티를 ScheduleResponseDto로 변환
    private ScheduleResponseDto convertToResponseDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getAuthor(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getDate(),
                schedule.getTime(),
                schedule.getStatus(),
                schedule.getCreatedAt(),
                schedule.getLastUpdatedAt()
        );
    }
}
