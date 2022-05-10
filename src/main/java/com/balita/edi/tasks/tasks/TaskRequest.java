package com.balita.edi.tasks.tasks;

import com.balita.edi.applicative.Environment;
import com.balita.edi.applicative.TypeFlux;
import com.balita.edi.applicative.planning.Planning;
import com.balita.edi.clients.Client;
import com.balita.edi.users.users.User;
import com.balita.edi.utils.enumvalidator.EnumNamePattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank
    private String flowName;
    @NotNull
    @EnumNamePattern(regexp = "PROD|QUALIF", message = "Invalid Environment")
    private Environment env;
    @NotNull
    @EnumNamePattern(regexp = "ENTRANT|SORTANT|INTERNE", message = "Invalid Type Flux")
    private TypeFlux typeFlux;
    private String description;
    @NotNull
    private Client client;
    @NotNull
    private String entryPoint;
    @NotNull
    private Planning planning;
    private Boolean active;
    private User responsible;
    private User backup;


}
