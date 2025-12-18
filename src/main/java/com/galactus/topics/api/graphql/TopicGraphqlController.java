package com.galactus.topics.api.graphql;

import com.galactus.topics.application.TopicService;
import com.galactus.topics.dto.TopicDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class TopicGraphqlController {
    private final TopicService service;

    public TopicGraphqlController(TopicService service) {
        this.service = service;
    }

    @QueryMapping
    public List<TopicDto> getTopics() {
        return service.findAll();
    }

    @QueryMapping
    public TopicDto getTopicById(@Argument Integer id) {
        return service.getById(id);
    }
}
