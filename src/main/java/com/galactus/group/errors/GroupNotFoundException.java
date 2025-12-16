package com.galactus.group.errors;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long groupId) {
        super("Group with ID: " + groupId + " does not exist");
    }
}
