package com.jdbctodoapp.service;

import com.jdbctodoapp.entity.Todo;
import com.jdbctodoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll(Boolean isCompleted) {
        if (isCompleted != null) {
            return repository.findByCompleted(isCompleted);
        }
        return repository.findAll();
    }

    public Optional<Todo> getById(int id) {
        return repository.findById(id);
    }

    public Todo addTodo(Todo todo) {
        todo.setCreatedAt(Instant.now());
        return repository.save(todo);
    }

    public int update(Todo todo) {
        return repository.update(todo);
    }

    public int delete(int id) {
        return repository.deleteById(id);
    }
}
