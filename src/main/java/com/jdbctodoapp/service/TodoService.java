package com.jdbctodoapp.service;

import com.jdbctodoapp.entity.Todo;
import com.jdbctodoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll(Boolean isCompleted) {
        return repository.findAll(isCompleted);
    }

    public Optional<Todo> getById(int id) {
        return repository.findById(id);
    }

    public int addTodo(Todo todo) {
        return repository.save(todo);
    }

    public int update(Todo todo) {
        return repository.update(todo);
    }

    public int delete(Long id) {
        return repository.deleteById(id);
    }
}
