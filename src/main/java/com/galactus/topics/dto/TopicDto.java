package com.galactus.topics.dto;

import java.time.Instant;

public record TopicDto(
        Integer id,
        String displayName,
        String emoji,
        Instant createdAt,
        Instant updatedAt
) {
}
