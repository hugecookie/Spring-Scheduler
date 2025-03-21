package org.example.controller;

import org.example.dto.ScheduleDeleteRequestDto;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.dto.ScheduleUpdateRequestDto;
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
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
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

    // ✅ 일정 수정: PUT /schedules/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {

        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    // ✅ 일정 삭제: DELETE /schedules/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteRequestDto requestDto) {

        ScheduleResponseDto deletedSchedule = scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.ok(deletedSchedule);
    }
}
