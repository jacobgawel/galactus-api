package com.galactus.controllers.rest;

import com.galactus.domain.dto.GroupDto;
import com.galactus.domain.interfaces.GroupService;
import com.galactus.domain.models.CreateGroupRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDto> findAll() {
        return groupService.findAll();
    }

    @PostMapping
    public GroupDto create(@RequestBody CreateGroupRequest request) {
        return groupService.create(request);
    }

    @GetMapping("{groupId}")
    public Optional<GroupDto> getById(@PathVariable Long groupId) {
        return groupService.getById(groupId);
    }
}
