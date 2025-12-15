package com.galactus.group.api.rest;

import com.galactus.group.dto.GroupDto;
import com.galactus.group.application.GroupService;
import com.galactus.group.dto.CreateGroupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> findAll() {
        var groups = groupService.findAll();
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody CreateGroupRequest request) {
        var created = groupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<GroupDto> getById(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getById(groupId));
    }
}
