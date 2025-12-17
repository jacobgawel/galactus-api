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

        // positions in a record are fixed by the order of components in the record header
        // when the constructor is called, the assignments will be positional.
        // EXTRA INFO: the compiler enforces types, meaning u can't put and 'Int' in place of an 'Instant'
        // It won't however catch same type swaps e.g. if u position upvote/downvote wrong way round (both integers)
        return new ThreadDto(
                entity.getId(), // this is going to the first item in the record e.g. ID
                entity.getHashedId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getGroup().getId(),
                entity.getUpvoteCount(),
                entity.getDownvoteCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
