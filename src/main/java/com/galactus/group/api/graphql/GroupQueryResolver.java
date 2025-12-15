package com.galactus.group.api.graphql;

import com.galactus.group.domain.Group;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.persistence.GroupRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupQueryResolver {
    private final GroupRepository repository;

    public GroupQueryResolver(GroupRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    public Group groupById(@Argument Long id) {
        return repository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
    }
}
