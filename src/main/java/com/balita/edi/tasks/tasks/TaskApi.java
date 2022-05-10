package com.balita.edi.tasks.tasks;

import com.balita.edi.utils.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
        return ResponseEntity.ok(taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity taskCreate(@RequestBody @Valid TaskRequest taskRequest, HttpServletRequest request) {
        Task saved = taskRepository.save(
                Task.builder()
                        .description(taskRequest.getDescription())
                        .flowName(taskRequest.getFlowName())
                        .env(taskRequest.getEnv())
                        .typeFlux(taskRequest.getTypeFlux())
                        .client(taskRequest.getClient())
                        .entryPoint(taskRequest.getEntryPoint())
                        .planning(taskRequest.getPlanning())
                        .responsible(taskRequest.getResponsible())
                        .active(taskRequest.getActive())
                        .backup(taskRequest.getBackup())
                        .build()
        );
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/tasks/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity taskUpdate(@PathVariable("id") Long id, @RequestBody @Valid TaskRequest taskRequest) {
        Task existed = this.taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        existed.setDescription(taskRequest.getDescription());
        existed.setFlowName(taskRequest.getFlowName());
        existed.setEnv(taskRequest.getEnv());
        existed.setTypeFlux(taskRequest.getTypeFlux());
        existed.setClient(taskRequest.getClient());
        existed.setEntryPoint(taskRequest.getEntryPoint());
        existed.setPlanning(taskRequest.getPlanning());
        existed.setResponsible(taskRequest.getResponsible());
        existed.setBackup(taskRequest.getBackup());
        existed.setActive(taskRequest.getActive());

        this.taskRepository.save(existed);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity taskDelete(@PathVariable("id") Long id) {
        Task existed = this.taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        taskRepository.delete(existed);
        return ResponseEntity.noContent().build();
    }

}
