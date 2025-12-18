package com.galactus.topics.api.rest;

import com.galactus.topics.application.TopicService;
import com.galactus.topics.dto.CreateTopicRequest;
import com.galactus.topics.dto.TopicDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/topic")
public class TopicRestController {
    private final TopicService service;

    public TopicRestController(TopicService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> findAll() {
        var topics = service.findAll();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("{topicId}")
    public ResponseEntity<TopicDto> findById(@PathVariable Integer topicId) {
        return ResponseEntity.ok(service.getById(topicId));
    }

    @PostMapping
    public ResponseEntity<TopicDto> create(@RequestBody CreateTopicRequest request) {
        var created = service.create(request);
        return ResponseEntity.ok(created);
    }
}
