package com.example.demo.Controller;


import com.example.demo.Model.Todo;
import com.example.demo.Service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

//    public TodoController(TodoService todoService){
//        this.todoService = todoService;
//    }

    @GetMapping
//    public List<Todo> getAllTodos(){
//        return todoService.getAllTodos();
//    } ส่งข้อมูล List<Todo> กลับไปโดยตรง แต่ถ้า Database ไม่มีข้อมูล จะ return [] (list เปล่า) และ Spring จะส่ง 200 OK ให้โดยอัตโนมัติ
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }
//    เราสามารถกำหนด Status Code ได้ชัดเจน (200 OK)
//    กำหนด Status Code, Header, และ Error Handling ได้


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
//    public Todo createTodo(@RequestBody Todo todo){
//        return todoService.createTodo(todo);
//    }
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.createTodo(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo newTodo) {
        Todo updatedTodo = todoService.updateTodo(id, newTodo);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
