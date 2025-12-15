package com.galactus.thread.dto;

import java.time.Instant;

public record ThreadDto(
        Long id,
        String title,
        String content,
        Long groupId,
        int upvoteCount,
        int downvoteCount,
        Instant createdAt,
        Instant updatedAt
) {
}
