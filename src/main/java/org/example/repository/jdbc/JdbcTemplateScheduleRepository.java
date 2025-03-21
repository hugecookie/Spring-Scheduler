package org.example.repository.jdbc;

import org.example.entity.Schedule;
import org.example.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ✅ 일정 저장 (새로운 일정 추가)
    @Override
    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedules (title, description, author, date, time, password, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getAuthor(),
                schedule.getDate(),
                schedule.getTime(),
                schedule.getPassword(),
                schedule.getStatus()
        );

        // ✅ 생성된 ID 조회
        Long generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        schedule.setId(generatedId);
        return schedule;
    }

    // ✅ ID를 이용한 일정 조회
    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, new ScheduleRowMapper(), id);
        return schedules.stream().findFirst();
    }

    // ✅ 모든 일정 조회
    @Override
    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedules";
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    // ✅ id 값에 대응하는 일정 변경
    @Override
    public Schedule update(Schedule schedule) {
        String sql = "UPDATE schedules " +
                "SET title = ?, author = ?, last_updated_at = NOW() " +
                "WHERE id = ?";

        int rows = jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getId()
        );

        if (rows == 0) {
            throw new IllegalStateException("일정 수정 실패");
        }

        return findById(schedule.getId())
                .orElseThrow(() -> new IllegalStateException("수정 후 일정 조회 실패"));
    }

    // ✅ id 값에 대응하는 일정 삭제
    @Override
    public Schedule delete(Schedule schedule) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getId());
        return schedule; // 또는 삭제 전 데이터를 조회해서 반환해도 OK
    }

    // ✅ 비밀번호 검증 메서드
    public boolean validatePassword(Long id, String password) {
        String sql = "SELECT COUNT(*) FROM schedules WHERE id = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id, password);
        return count > 0;
    }

    // ✅ ID 확인 로직
    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM schedules WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    // ✅ Schedule 엔티티를 매핑하는 RowMapper
    private static class ScheduleRowMapper implements RowMapper<Schedule> {
        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Schedule(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("password"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("last_updated_at") != null ? rs.getTimestamp("last_updated_at").toLocalDateTime() : null
            );
        }
    }
}
