package com.stevenkolamkuzhiyil.SpringCrud.exception.throwable;

public class BranchNotFound extends RuntimeException {
    public BranchNotFound(long id) {
        super("Branch with ID " + id + " not found!");
    }
}
