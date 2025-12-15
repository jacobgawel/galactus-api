package com.galactus.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// make sure to update GroupPatch if this changes
@Getter
@Setter
public class UpdateGroupRequest {
    @NotBlank
    public Long id;

    @Size(max = 80)
    public String displayName;

    @Size(max = 500)
    public String description;

    @Size(max = 500)
    public String iconUrl;

    @Size(max = 500)
    public String bannerUrl;
    public Boolean nsfw; // boxed boolean meaning that this can be null
    public Boolean isPrivate;
}
