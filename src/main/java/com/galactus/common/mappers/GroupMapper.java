package com.galactus.common.mappers;

import com.galactus.group.domain.Group;
import com.galactus.group.dto.GroupDto;

public class GroupMapper {
    private GroupMapper() {
    }

    public static GroupDto toDto(Group entity) {
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
