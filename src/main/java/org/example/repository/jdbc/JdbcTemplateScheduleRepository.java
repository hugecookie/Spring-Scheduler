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
