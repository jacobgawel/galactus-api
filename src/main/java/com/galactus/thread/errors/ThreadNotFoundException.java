package com.galactus.thread.errors;

public class ThreadNotFoundException extends RuntimeException {
    public ThreadNotFoundException(Long threadId) {
        super("Thread with ID: " + threadId + " does not exist");
    }
}
