package com.example.gtable.TODO;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public String add() {
        String todo = "TODO";
        String todoList = "TODO LIST";
        return todo;
    }
}
