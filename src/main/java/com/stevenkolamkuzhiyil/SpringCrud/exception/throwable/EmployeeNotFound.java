package com.stevenkolamkuzhiyil.SpringCrud.exception.throwable;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound(long id) {
        super("Employee with ID " + id + " not found!");
    }
}
