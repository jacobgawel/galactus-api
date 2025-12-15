package com.galactus.group.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGroupRequest {
    public Long id;
    public String displayName;
    public String description;
    public String iconUrl;
    public String bannerUrl;
    public Boolean nsfw; // boxed boolean meaning that this can be null
    public Boolean isPrivate;
}
