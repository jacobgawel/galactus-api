package com.galactus.topics.errors;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(Integer topicId) {
        super("Topic with ID: " + topicId + " does not exist");
    }
}
