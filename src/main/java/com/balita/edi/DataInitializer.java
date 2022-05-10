package com.balita.edi;

import com.balita.edi.applicative.Environment;
import com.balita.edi.applicative.TypeFlux;
import com.balita.edi.applicative.planning.Planning;
import com.balita.edi.applicative.planning.PlanningRepository;
import com.balita.edi.clients.Client;
import com.balita.edi.clients.ClientRepository;
import com.balita.edi.tasks.tasks.Task;
import com.balita.edi.tasks.tasks.TaskRepository;
import com.balita.edi.users.users.User;
import com.balita.edi.users.users.UserRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().firstName("Rico").lastName("FAUCHARD").email("randrianaivo@iqera.com").initial("RF").build();
        User user2 = User.builder().firstName("Walid").lastName("SAIDI").email("wsaidi@iqera.com").initial("WS").build();
        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(user2);

        log.debug("initializing clients data...");
        String[] plannings = {"ETL Tool", "Cron", "CTRL-M", "Autre", "GLPI"};
        List<Planning> plans = new ArrayList<>();
        for (String v : plannings) {
            Planning plan = Planning.builder().name(v).build();
            plans.add(plan);
            planningRepository.saveAndFlush(plan);
        }

        log.debug("initializing clients data...");
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Faker faker = new Faker();
            Client client = Client.builder()
                    .name(faker.team().name())
                    .domaine(faker.address().cityName())
                    .build();
            clients.add(client);
            clientRepository.saveAndFlush(client);
        }
        log.debug("printing all clients...");
        this.clientRepository.findAll().forEach(t -> log.debug(" Client :" + t.toString()));

        log.debug("initializing tasks data...");
        for (int i = 0; i < 5; i++) {
            Faker faker = new Faker();
            taskRepository.saveAndFlush(
                    Task.builder()
                            .flowName(faker.company().name())
                            .description(faker.company().buzzword())
                            .env((i % 2 == 0) ? Environment.PROD : Environment.QUALIF)
                            .typeFlux(TypeFlux.ENTRANT)
                            .client(clients.get(i))
                            .entryPoint("//nasfic.group.local/PRD/Bora/")
                            .planning(plans.get(i))
                            .active(true)
                            .responsible(user)
                            .backup(user2)
                            .build()
            );
        }

        log.debug("printing all tasks...");
        this.taskRepository.findAll().forEach(t -> log.debug(" Task :" + t.toString()));
    }
}
