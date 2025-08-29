package com.jdbctodoapp.entity;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Todo {
    private int id;
    private String title;
    private Boolean isCompleted;
    private Instant createdAt;
}