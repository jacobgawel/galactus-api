package com.galactus.group.api.graphql;

import com.galactus.group.application.GroupService;
import com.galactus.group.dto.GroupDto;
import com.galactus.group.dto.UpdateGroupRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupQueryResolver {
    private final GroupService service;

    public GroupQueryResolver(GroupService service) {
        this.service = service;
    }

    @QueryMapping
    public GroupDto groupById(@Argument Long id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<GroupDto> getGroups() {
        return service.findAll();
    }

    @MutationMapping
    public GroupDto updateGroup(@Argument("input")UpdateGroupRequest input) {
        return service.update(input);
    }
}
