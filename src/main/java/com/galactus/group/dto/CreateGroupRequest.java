package com.galactus.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class CreateGroupRequest {
    @NotBlank(message = "slug is required")
    @Size(min = 3, max = 30, message = "slug must be between 3 and 30 characters")
    private String slug;

    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    @NotBlank(message = "displayName is required")
    @Size(min = 2, max = 50, message = "displayName must be between 2 and 50 characters")
    private String displayName;

    @NotNull(message = "topicId must not be null")
    @Range(min = 1)
    private Integer topicId;

    private boolean nsfw;
    private boolean isPrivate;
}
