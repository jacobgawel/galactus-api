package com.galactus.thread.api.rest;

import com.galactus.thread.application.ThreadService;
import com.galactus.thread.dto.CreateThreadRequest;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.thread.dto.UpdateThreadRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/thread")
public class ThreadRestController {
    private final ThreadService service;

    public ThreadRestController(ThreadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ThreadDto>> findAll() {
        var threads = service.findAll();
        return ResponseEntity.ok(threads);
    }

    @GetMapping("{threadId}")
    public ResponseEntity<ThreadDto> getById(@PathVariable Long threadId) {
        return ResponseEntity.ok(service.getById(threadId));
    }

    @PostMapping
    public ResponseEntity<ThreadDto> create(@Valid @RequestBody CreateThreadRequest request) {
        var created = service.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<ThreadDto> update(@Valid @RequestBody UpdateThreadRequest request) {
        var updated = service.update(request);
        return ResponseEntity.ok(updated);
    }
}
