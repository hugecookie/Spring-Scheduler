package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ScheduleDeleteRequestDto;
import org.example.dto.ScheduleCreateRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.dto.ScheduleUpdateRequestDto;
import org.example.dto.PagingResponseDto;
import org.example.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // ✅ 스케줄 컨트롤러 생성자 (의존성 주입)
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // ✅ [일정 생성] POST /schedules
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleCreateRequestDto requestDto) {
        ScheduleResponseDto createdSchedule = scheduleService.createSchedule(requestDto);
        return ResponseEntity.ok(createdSchedule);
    }

    //  ✅ [특정 일정 조회] GET /schedules/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        Optional<ScheduleResponseDto> schedule = scheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ [전체 일정 조회] GET /schedules
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // ✅[페이지네이션] GET /paged 10개씩
    @GetMapping("/paged")
    public ResponseEntity<PagingResponseDto<ScheduleResponseDto>> getSchedulesWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagingResponseDto<ScheduleResponseDto> result = scheduleService.getSchedules(page, size);
        return ResponseEntity.ok(result);
    }

    // ✅ 일정 수정: PUT /schedules/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto requestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    // ✅ 일정 삭제: DELETE /schedules/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDeleteRequestDto requestDto) {
        ScheduleResponseDto deletedSchedule = scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.ok(deletedSchedule);
    }
}
