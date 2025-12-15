package com.galactus.group.dto;

import lombok.Getter;

@Getter
public class CreateGroupRequest {
    public String slug;
    public String description;
    public String displayName;
    public boolean nsfw;
    public boolean isPrivate;
}
