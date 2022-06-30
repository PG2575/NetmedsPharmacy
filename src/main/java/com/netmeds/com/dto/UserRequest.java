package com.netmeds.com.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "username shouldn't be null")
    private String userName;

    @NotBlank(message = "password shouldn't be blank")
    private String password;

    private boolean active;

    @NotBlank
    private String roles;
}
