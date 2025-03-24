package org.example.repository.jdbc;

import org.example.entity.Author;
import org.example.entity.Schedule;
import org.example.entity.ScheduleStatus;
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
        String sql = "INSERT INTO schedules (title, description, author_id, date, time, password, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getAuthor().getId(), // ✅ author 객체에서 id만 추출
                schedule.getDate(),
                schedule.getTime(),
                schedule.getPassword(),
                schedule.getStatus().toString()
        );

        Long generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        schedule.setId(generatedId);
        return schedule;
    }

    // ✅ ID를 이용한 일정 조회
    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = """
            SELECT s.*,
                   a.id AS author_id,
                   a.name AS author_name,
                   a.email AS author_email,
                   a.created_at AS author_created_at,
                   a.updated_at AS author_updated_at
            FROM schedules s
            JOIN authors a ON s.author_id = a.id
            WHERE s.id = ?
        """;
        List<Schedule> schedules = jdbcTemplate.query(sql, new ScheduleRowMapper(), id);
        return schedules.stream().findFirst();
    }

    // ✅ 모든 일정 조회
    @Override
    public List<Schedule> findAll() {
        String sql = """
            SELECT s.*,
                   a.id AS author_id,
                   a.name AS author_name,
                   a.email AS author_email,
                   a.created_at AS author_created_at,
                   a.updated_at AS author_updated_at
            FROM schedules s
            JOIN authors a ON s.author_id = a.id
        """;
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    // ✅ id 값에 대응하는 일정 변경
    @Override
    public Schedule update(Schedule schedule) {
        String sql = """
            UPDATE schedules
            SET title = ?, description = ?, author_id = ?, last_updated_at = NOW()
            WHERE id = ?
        """;

        int rows = jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getAuthor().getId(), // ✅ 변경된 author_id
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
        return schedule;
    }

    // ✅ 비밀번호 검증 메서드
    @Override
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

    @Override
    public List<Schedule> findAllWithPaging(int offset, int limit) {
        String sql = """
        SELECT s.*,
               a.id AS author_id,
               a.name AS author_name,
               a.email AS author_email,
               a.created_at AS author_created_at,
               a.updated_at AS author_updated_at
        FROM schedules s
        JOIN authors a ON s.author_id = a.id
        ORDER BY s.created_at DESC
        LIMIT ? OFFSET ?
    """;
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), limit, offset);
    }

    // ✅ Schedule 엔티티를 매핑하는 RowMapper (작성자 연관 포함, 생성자만 사용)
    private static class ScheduleRowMapper implements RowMapper<Schedule> {
        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(
                    rs.getLong("author_id"),
                    rs.getString("author_name"),
                    rs.getString("author_email"),
                    rs.getTimestamp("author_created_at").toLocalDateTime(),
                    rs.getTimestamp("author_updated_at").toLocalDateTime()
            );

            return new Schedule(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    author,
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("password"),
                    ScheduleStatus.from(rs.getString("status")),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("last_updated_at") != null
                            ? rs.getTimestamp("last_updated_at").toLocalDateTime()
                            : null
            );
        }
    }
}
