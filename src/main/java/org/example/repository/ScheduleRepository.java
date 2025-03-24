package org.example.repository;

import org.example.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule); // ✅ 일정 저장
    Optional<Schedule> findById(Long id); // ✅ ID로 일정 조회
    List<Schedule> findAll(); // ✅ 모든 일정 조회
    Schedule update(Schedule schedule); // ✅ 수정 후 해당 일정 반환
    Schedule delete(Schedule schedule); // ✅ 삭제 후 해당 일정 반환
    boolean validatePassword(Long id, String password); // ✅ 비밀번호 검증 추가
    boolean existsById(Long id); // ✅ ID 일정 확인
    List<Schedule> findAllWithPaging(int offset, int limit);
    long count(); // 전체 일정 개수 반환
}
