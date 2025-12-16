package com.galactus.group.api.rest;

import com.galactus.group.dto.GroupDto;
import com.galactus.group.application.GroupService;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.dto.UpdateGroupRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/group")
public class GroupRestController {
    private final GroupService service;

    public GroupRestController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> findAll() {
        var groups = service.findAll();
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@Valid @RequestBody CreateGroupRequest request) {
        var created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<GroupDto> update(@Valid @RequestBody UpdateGroupRequest request) {
        var updated = service.update(request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<GroupDto> getById(@PathVariable Long groupId) {
        return ResponseEntity.ok(service.getById(groupId));
    }
}
