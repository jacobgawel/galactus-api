package com.galactus.common.mappers;

import com.galactus.topics.domain.Topic;
import com.galactus.topics.dto.TopicDto;

public class TopicMapper {
    private TopicMapper() {
    }

    public static TopicDto toDto(Topic entity) {
        if (entity == null) {
            return null;
        }

        return new TopicDto(
                entity.getId(),
                entity.getDisplayName(),
                entity.getEmoji(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
