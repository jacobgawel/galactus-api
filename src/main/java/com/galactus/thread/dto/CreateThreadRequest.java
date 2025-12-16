package com.galactus.thread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateThreadRequest {
    @NotBlank(message = "title is required")
    @Size(min = 3, max = 50, message = "slug must be between 3 and 50 characters")
    public String title;

    @Size(min = 3, max = 40_000, message = "content must be between 3 and 40,000 characters")
    public String content;

    @NotNull(message = "GroupID is required")
    @Positive(message = "GroupID must be a positive number")
    public Long groupId;
}
