package org.example.service.impl;

import org.example.dto.*;
import org.example.entity.Author;
import org.example.entity.Schedule;
import org.example.entity.ScheduleStatus;
import org.example.exception.PasswordMismatchException;
import org.example.exception.ScheduleNotFoundException;
import org.example.repository.AuthorRepository;
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
    private final AuthorRepository authorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    // ✅ [일정 생성] 새로운 일정 저장
    @Override
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto) {
        AuthorDto authorDto = requestDto.getAuthor();

        Author author = authorRepository.findByEmail(authorDto.getEmail())
                .orElseGet(() -> authorRepository.save(
                        new Author(null, authorDto.getName(), authorDto.getEmail(), null, null)
                ));

        Schedule schedule = new Schedule(
                null,
                requestDto.getTitle(),
                requestDto.getDescription(),
                author,
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getPassword(),
                ScheduleStatus.SCHEDULED,
                LocalDateTime.now(),
                null
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToResponseDto(savedSchedule);
    }

    // ✅ [특정 일정 조회] {id} 값에 맞춰서 해당 일정을 조회
    @Override
    public Optional<ScheduleResponseDto> getScheduleById(Long id) {
        // ✅ 해당 일정 존재 여부
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("해당 일정이 존재하지 않습니다.");
        }

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
        // ✅ 해당 일정 존재 여부
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("해당 일정이 존재하지 않습니다.");
        }

        // ✅ 비밀번호 검증
        if (!scheduleRepository.validatePassword(id, requestDto.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        Schedule schedule = new Schedule(
                id,
                requestDto.getTitle(),
                requestDto.getDescription(),
                existing.getAuthor(),
                requestDto.getDate(),
                requestDto.getTime(),
                existing.getPassword(),
                existing.getStatus(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        Schedule updated = scheduleRepository.update(schedule);
        return convertToResponseDto(updated);
    }

    @Override
    public ScheduleResponseDto deleteSchedule(Long id, ScheduleDeleteRequestDto requestDto) {
        // ✅ 비밀번호 검증
        if (!scheduleRepository.validatePassword(id, requestDto.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // ✅ 해당 일정 존재 여부 검증 후 schedule 생성
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        // ✅ 삭제 기능 수행
        Schedule deleted = scheduleRepository.delete(schedule);
        return convertToResponseDto(deleted);
    }

    @Override
    public PagingResponseDto<ScheduleResponseDto> getSchedules(int page, int size) {
        int offset = page * size;
        List<Schedule> schedules = scheduleRepository.findAllWithPaging(offset, size);
        List<ScheduleResponseDto> content = schedules.stream()
                .map(this::convertToResponseDto)
                .toList();

        long totalElements = scheduleRepository.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PagingResponseDto<>(content, page, size, totalPages, totalElements);
    }

    // ✅ Schedule 엔티티를 ScheduleResponseDto로 변환
    private ScheduleResponseDto convertToResponseDto(Schedule schedule) {
        Author author = schedule.getAuthor();
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                author.getName(),
                author.getEmail(),
                schedule.getDate(),
                schedule.getTime(),
                schedule.getStatus().name(),
                schedule.getCreatedAt(),
                schedule.getLastUpdatedAt()
        );
    }
}
