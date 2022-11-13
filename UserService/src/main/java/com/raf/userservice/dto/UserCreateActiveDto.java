package com.raf.userservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserCreateActiveDto {

    @NotBlank
    @Pattern(regexp = "(yes|no)", message = "Active must be yes or no!")
    private String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
