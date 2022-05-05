package com.balita.edi.tasks.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity vehicleNotFound(TaskNotFoundException ex, WebRequest request) {
        log.debug("handling VehicleNotFoundException...");
        return ResponseEntity.notFound().build();
    }
}
