package org.example.repository;

import org.example.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule); // 일정 저장
    Optional<Schedule> findById(Long id); // ID로 일정 조회
    List<Schedule> findAll(); // 모든 일정 조회
}
