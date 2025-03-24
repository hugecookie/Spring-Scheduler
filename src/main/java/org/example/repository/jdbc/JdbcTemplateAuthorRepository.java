package org.example.repository.jdbc;

import org.example.entity.Author;
import org.example.repository.AuthorRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Author> findByEmail(String email) {
        String sql = "SELECT * FROM authors WHERE email = ?";
        List<Author> authors = jdbcTemplate.query(sql, new AuthorRowMapper(), email);
        return authors.stream().findFirst();
    }

    @Override
    public Author save(Author author) {
        String sql = "INSERT INTO authors (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, author.getName(), author.getEmail());

        // INSERT 후 id 조회
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // 작성자 다시 조회해서 반환 (생성자 방식으로 반환)
        String query = "SELECT * FROM authors WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new AuthorRowMapper(), id);
    }

    // ✅ Author 엔티티를 매핑하는 RowMapper (생성자 방식!)
    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }
    }
}
