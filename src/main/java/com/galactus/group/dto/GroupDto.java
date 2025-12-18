package com.galactus.group.dto;

import java.time.Instant;

public record GroupDto (
        Long id,
        String slug,
        String displayName,
        String hashedId,
        String description,
        String iconUrl,
        String bannerUrl,
        Integer topicId,
        boolean nsfw,
        boolean isPrivate,
        Instant createdAt,
        Instant updatedAt
) {}
