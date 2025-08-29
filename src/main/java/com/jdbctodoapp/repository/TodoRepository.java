package com.jdbctodoapp.repository;

import com.jdbctodoapp.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Todo> rowMapper = (rs, rowNum) -> {
        Todo todo = new Todo();
        todo.setId(rs.getLong("id"));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getBoolean("is_completed"));
        todo.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return todo;
    };

    // Récupère toutes les tâches
    public List<Todo> findAll() {
        String sql = "SELECT id, title, is_completed, created_at FROM todos";
        return jdbcTemplate.query(sql, this.rowMapper); [4]
    }
    
    public int save(Todo todo) {
        String sql = "INSERT INTO todos (title) VALUES (?)";
        return jdbcTemplate.update(sql, todo.getTitle()); [1]
    }

    public int update(Todo todo) {
        String sql = "UPDATE todos SET title =?, is_completed =? WHERE id =?";
        return jdbcTemplate.update(sql, todo.getTitle(), todo.isCompleted(), todo.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM todos WHERE id =?";
        return jdbcTemplate.update(sql, id);
    }
}