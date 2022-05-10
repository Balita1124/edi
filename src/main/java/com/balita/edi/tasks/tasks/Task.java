package com.balita.edi.tasks.tasks;

import com.balita.edi.applicative.Environment;
import com.balita.edi.applicative.TypeFlux;
import com.balita.edi.applicative.planning.Planning;
import com.balita.edi.clients.Client;
import com.balita.edi.users.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Flow Name is mandatory")
    private String flowName;
    @NotNull
    private Environment env;
    @NotNull
    private TypeFlux typeFlux;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;
    @NotNull
    private String entryPoint;
    @ManyToOne
    @JoinColumn(name = "planningId", nullable = false)
    private Planning planning;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "responsibleId")
    private User responsible;
    @ManyToOne
    @JoinColumn(name = "backupId")
    private User backup;
}
