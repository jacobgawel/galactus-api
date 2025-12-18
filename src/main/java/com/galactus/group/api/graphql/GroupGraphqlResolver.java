package com.galactus.group.api.graphql;

import com.galactus.group.dto.GroupDto;
import com.galactus.thread.application.ThreadService;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.topics.application.TopicService;
import com.galactus.topics.dto.TopicDto;
import jakarta.annotation.Nullable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupGraphqlResolver {
    private final ThreadService threadService;
    private final TopicService topicService;

    public GroupGraphqlResolver(ThreadService threadService, TopicService topicService) {
        this.threadService = threadService;
        this.topicService = topicService;
    }

    // the SchemaMapping maps the type name to a field within that type
    // this is used to resolve/fetch potentially related objects
    @SchemaMapping(typeName = "Group", field = "threads")
    public List<ThreadDto> threads(
            GroupDto groups,
            // these need to be nullable here too since in the schema they are nullable
            @Argument @Nullable Integer limit,
            @Argument @Nullable Integer offset
    ) {
        int effectiveLimit = (limit != null) ? limit : 20;
        int effectiveOffset = (offset != null) ? offset : 0;
        return threadService.findByGroupIdPaged(groups.id(), effectiveLimit, effectiveOffset);
    }

    @SchemaMapping(typeName = "Group", field = "topic")
    public TopicDto topic(GroupDto group) {
        return topicService.getById(group.topicId());
    }
}
