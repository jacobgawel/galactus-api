package com.galactus.topics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTopicRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String displayName;

    private String emoji;

}
