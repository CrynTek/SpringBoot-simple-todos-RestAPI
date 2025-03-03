package com.example.demo.Service;

import com.example.demo.Model.Todo;
import com.example.demo.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

//    public TodoService(TodoRepository todoRepository){
//        this.todoRepository = todoRepository;
//    } use @RequiredArgsConstructor instead

    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id){
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    @Transactional
    public Todo updateTodo(Long id,Todo newTodo){
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setCompleted(newTodo.isCompleted());
                    return todoRepository.save(todo);
                })
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @Transactional
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found");
        }
        todoRepository.deleteById(id);
    }
}
