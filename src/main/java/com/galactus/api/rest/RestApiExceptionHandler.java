package com.galactus.api.rest;

import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.errors.SlugAlreadyTakenException;
import com.galactus.thread.errors.ThreadNotFoundException;
import com.galactus.topics.errors.TopicNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global REST exception handler for the API.
 *
 * <p>
 * This class centralizes error handling for all {@code @RestController} endpoints.
 * Instead of returning ad-hoc error DTOs (or letting exceptions bubble up into a generic 500),
 * we convert known domain exceptions into consistent HTTP responses using Spring's
 * {@link org.springframework.http.ProblemDetail}.
 * </p>
 **/
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(SlugAlreadyTakenException.class)
    public ResponseEntity<ProblemDetail> handleSlugTaken(SlugAlreadyTakenException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setTitle("Group already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleGroupNotFound(GroupNotFoundException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Group not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(ThreadNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleThreadNotFound(ThreadNotFoundException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Thread not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleTopicNotFound(TopicNotFoundException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Topic not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }
}
