package com.jdbctodoapp.controller;

import com.jdbctodoapp.entity.Todo;
import com.jdbctodoapp.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAll(@RequestParam(value = "isCompleted", required = false) Boolean isCompleted) {
        return service.getAll(isCompleted);
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable int id) {
        return service.getById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return service.addTodo(todo);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable int id, @RequestBody Todo todo) {
        todo.setId(id);
        service.update(todo);
        return "Todo updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Todo deleted";
    }
}