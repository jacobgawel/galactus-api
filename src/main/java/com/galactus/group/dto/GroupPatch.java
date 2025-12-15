package com.galactus.group.dto;

// make sure to update UpdateGroupRequest if this changes
public record GroupPatch(
        String displayName,
        String description,
        Boolean nsfw,
        Boolean isPrivate,
        String iconUrl,
        String bannerUrl
) {
}
