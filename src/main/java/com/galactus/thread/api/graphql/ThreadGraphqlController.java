package com.galactus.thread.api.graphql;

import com.galactus.thread.application.ThreadService;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.thread.dto.UpdateThreadRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ThreadGraphqlController {
    private final ThreadService service;

    public ThreadGraphqlController(ThreadService service) {
        this.service = service;
    }

    @QueryMapping
    public List<ThreadDto> getThreads() {
        return service.findAll();
    }

    @QueryMapping
    public ThreadDto getThreadById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public ThreadDto updateThread(@Argument("input") UpdateThreadRequest input) {
        return service.update(input);
    }
}
