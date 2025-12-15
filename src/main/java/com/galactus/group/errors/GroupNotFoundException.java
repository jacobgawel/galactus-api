package com.galactus.group.errors;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long message) {
        super("Group with ID: " + message + " does not exist");
    }
}
