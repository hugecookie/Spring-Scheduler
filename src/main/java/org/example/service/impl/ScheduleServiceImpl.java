package org.example.service.impl;

import org.example.dto.ScheduleDeleteRequestDto;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.dto.ScheduleUpdateRequestDto;
import org.example.entity.Schedule;
import org.example.repository.ScheduleRepository;
import org.example.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        // ✅ 존재하지 않는 일정이면 404 응답
        if (!scheduleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");
        }

        // ✅ 비밀번호 틀리면 403 응답
        if (!scheduleRepository.validatePassword(id, requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        // ✅ 정상 수정 로직
        Schedule schedule = new Schedule(id, requestDto.getTitle(), requestDto.getAuthor());

        Schedule updated = scheduleRepository.update(schedule);
        return convertToResponseDto(updated);
    }


    @Override
    public ScheduleResponseDto deleteSchedule(Long id, ScheduleDeleteRequestDto requestDto) {
        // ✅ 비밀번호 틀리면 403 응답
        if (!scheduleRepository.validatePassword(id, requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        // ✅ 일정 조회 (심볼 오류 해결)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        // ✅ 삭제 기능 수행
        Schedule deleted = scheduleRepository.delete(schedule);
        return convertToResponseDto(deleted);
    }

    // ✅ Schedule 엔티티를 ScheduleResponseDto로 변환
    private ScheduleResponseDto convertToResponseDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getAuthor(),
                schedule.getDate(),
                schedule.getTime(),
                schedule.getStatus(),
                schedule.getCreatedAt(),
                schedule.getLastUpdatedAt()
        );
    }
}
