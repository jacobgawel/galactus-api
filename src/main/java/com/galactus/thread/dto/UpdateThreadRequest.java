package com.galactus.thread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateThreadRequest {
    @NotBlank
    @Positive(message = "ThreadID must be a positive number")
    Long id;
    String title;
    String content;
}
