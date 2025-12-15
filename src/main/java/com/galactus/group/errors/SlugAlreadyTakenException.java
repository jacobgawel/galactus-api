package com.galactus.group.errors;

public class SlugAlreadyTakenException extends RuntimeException {
    public SlugAlreadyTakenException(String message) {
        super("The group name already exists: " + message);
    }
}
