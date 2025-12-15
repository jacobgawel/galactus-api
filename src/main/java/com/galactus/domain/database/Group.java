package com.galactus.domain.database;


import com.galactus.domain.constants.ContentTypePrefixes;
import com.galactus.domain.helpers.Base36Codec;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "groups"
)
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private String hashedId;

    // human-readable title like "wow", "gta6" or "battlefield"
    @NotBlank
    @Size(min = 2, max = 40)
    @Column(length = 40)
    private String slug;

    @NotBlank
    @Size(min = 2, max = 500)
    @Column(length = 80)
    private String displayName;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String iconUrl;

    @Column(length = 500)
    private String bannerUrl;

    @Column(nullable = false)
    private boolean nsfw = false;

    @Column(nullable = false)
    private boolean isPrivate = false;

    // TODO: eventually we need to add a createdBy user field
    // furthermore we will need to further decide how to showcase mods and admins
    // Future enhancements: transferring ownership, more customization via some json properties

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PreUpdate
    @PrePersist
    public void normalize() {
        slug = slug.trim().toLowerCase();
        displayName = displayName.trim();
        description = description.trim();
    }
}
