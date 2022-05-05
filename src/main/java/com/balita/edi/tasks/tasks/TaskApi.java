package com.balita.edi.tasks.tasks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/tasks")
public class TaskApi {

    private TaskRepository taskRepository;

    public TaskApi(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity tasksList() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity taskGet(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity taskCreate(@RequestBody TaskRequest taskRequest, HttpServletRequest request) {
        Task saved = taskRepository.save(Task.builder().description(taskRequest.getDescription()).flowName(taskRequest.getFlowName()).build());
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromContextPath(request).
                        path("/v1/tasks/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity taskUpdate(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        Task existed = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        existed.setDescription(taskRequest.getDescription());
        existed.setFlowName(taskRequest.getFlowName());

        this.taskRepository.save(existed);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity taskDelete(@PathVariable("id") Long id) {
        Task existed = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(existed);
        return ResponseEntity.noContent().build();
    }

}
