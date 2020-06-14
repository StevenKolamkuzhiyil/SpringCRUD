package com.stevenkolamkuzhiyil.SpringCrud.exception.throwable;

import javax.validation.constraints.Email;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(@Email String email) {
        super("User with email " + email + " not found");
    }
}
