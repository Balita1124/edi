package com.balita.edi.users.users;

import javax.validation.constraints.NotNull;

public class UserRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String initial;
    @NotNull
    private String email;
}
