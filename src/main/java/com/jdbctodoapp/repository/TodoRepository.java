package com.jdbctodoapp.repository;

import com.jdbctodoapp.entity.Todo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Todo> rowMapper = (rs, rowNum) -> {
        Todo todo = new Todo();
        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getObject("is_completed", Boolean.class));
        todo.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return todo;
    };

    public List<Todo> findAll() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, rowMapper);
    }


    public Optional<Todo> findById(int id) {
        String sql = "SELECT id, title, is_completed, created_at FROM tasks WHERE id = ?";
        try {
            Todo todo = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Todo save(Todo todo) {
        String sql = "INSERT INTO tasks (title, is_completed, created_at) VALUES (?, ?, ?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, todo.getTitle());
            ps.setBoolean(2, todo.isCompleted());
            ps.setTimestamp(3, Timestamp.from(todo.getCreatedAt()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            todo.setId(keyHolder.getKey().intValue());
        }
        return todo;
    }


    public int update(Todo todo) {
        String sql = "UPDATE tasks SET title = ?, is_completed = ? WHERE id = ?";
        return jdbcTemplate.update(sql, todo.getTitle(), todo.isCompleted(), todo.getId());
    }


    public int deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Todo> findByCompleted(boolean isCompleted) {
        String sql = "SELECT id, title, is_completed, created_at FROM tasks WHERE is_completed = ?";
        return jdbcTemplate.query(sql, rowMapper, isCompleted);
    }

    public int deleteAll() {
        String sql = "DELETE FROM tasks";
        return jdbcTemplate.update(sql);
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM tasks";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int deleteCompleted() {
        String sql = "DELETE FROM tasks WHERE is_completed = true";
        return jdbcTemplate.update(sql);
    }
}
