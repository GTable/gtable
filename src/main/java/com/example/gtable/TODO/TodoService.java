package com.example.gtable.TODO;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    /**
     * Constructs a new TodoService with the specified TodoRepository.
     *
     * @param todoRepository the repository used for managing todo items
     */
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /****
     * Returns a hardcoded string representing a todo item.
     *
     * @return the string "TODO"
     */
    public String add() {
        String todo = "TODO";
        String todoList = "TODO LIST";
        return todo;
    }
}
