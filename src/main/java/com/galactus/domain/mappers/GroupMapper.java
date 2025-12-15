package com.galactus.domain.mappers;

import com.galactus.domain.database.Group;
import com.galactus.domain.dto.GroupDto;

public class GroupMapper {
    private GroupMapper() {
    }

    public static GroupDto toResponse(Group entity) {
        if (entity == null) {
            return null;
        }

        return new GroupDto(
                entity.getId(),
                entity.getSlug(),
                entity.getDisplayName(),
                entity.getHashedId(),
                entity.getDescription(),
                entity.getIconUrl(),
                entity.getBannerUrl(),
                entity.isNsfw(),
                entity.isPrivate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
