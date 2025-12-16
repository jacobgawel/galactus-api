package com.galactus.group.api.graphql;

import com.galactus.group.dto.GroupDto;
import com.galactus.thread.application.ThreadService;
import com.galactus.thread.dto.ThreadDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupThreadsGraphqlResolver {
    private final ThreadService service;

    public GroupThreadsGraphqlResolver(ThreadService service) {
        this.service = service;
    }

    // the SchemaMapping maps the type name to a field within that type
    // this is used to resolve/fetch potentially related objects
    @SchemaMapping(typeName = "Group", field = "threads")
    public List<ThreadDto> threads(
            GroupDto groups,
            @Argument int limit,
            @Argument int offset
    ) {
        return service.findByGroupIdPaged(groups.id(), limit, offset);
    }
}
