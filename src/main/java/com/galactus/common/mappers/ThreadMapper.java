package com.galactus.common.mappers;

import com.galactus.thread.domain.Thread;
import com.galactus.thread.dto.ThreadDto;

public class ThreadMapper {
    private ThreadMapper() {
    }

    public static ThreadDto toDto(Thread entity) {
        if (entity == null) {
            return null;
        }

        return new ThreadDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getGroup().getId(),
                entity.getUpvoteCount(),
                entity.getDownvoteCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
