package com.balita.edi;

import com.balita.edi.tasks.tasks.Task;
import com.balita.edi.tasks.tasks.TaskRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("initializing tasks data...");
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            taskRepository.saveAndFlush(Task.builder().flowName(faker.company().name()).description(faker.company().buzzword()).build());
        }

        log.debug("printing all tasks...");
        this.taskRepository.findAll().forEach(t -> log.debug(" Task :" + t.toString()));
    }
}
