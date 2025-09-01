package com.jdbctodoapp.entity;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Todo {
    private int id;
    private String title;
    private boolean isCompleted;
    private Instant createdAt;

    public Todo(String title) {
        this.title = title;
        this.isCompleted = false;
        this.createdAt = Instant.now();
    }
}