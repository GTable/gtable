package com.example.gtable.TODO;

import com.example.gtable.global.api.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private TodoService todoService;
    @GetMapping("TODO")
    public ResponseEntity<?> createBoard() {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    todoService.add()
                )
            );
    }
}