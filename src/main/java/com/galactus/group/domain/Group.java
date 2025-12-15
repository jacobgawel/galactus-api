package com.galactus.group.domain;


import com.galactus.group.dto.GroupPatch;
import com.galactus.thread.domain.Thread;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "groups",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_groups_slug", columnNames = "slug")
        }
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

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Thread> threads = new ArrayList<>();

    public void addThread(Thread thread) {
        threads.add(thread);
        thread.setGroup(this);
    }

    public void removeThread(Thread thread) {
        threads.remove(thread);
        thread.setGroup(null);
    }

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
        if (slug != null) {
            slug = slug.trim();
        }
        if (displayName != null) {
            displayName = displayName.trim();
        }
        if (description != null) {
            description = description.trim();
        }
    }

    public void apply(GroupPatch request) {
        if (request.displayName() != null) {
            setDisplayName(request.displayName());
        }
        if (request.description() != null) {
            setDescription(request.description());
        }
        if (request.nsfw() != null) {
            setNsfw(request.nsfw());
        }
        if (request.isPrivate() != null) {
            setPrivate(request.isPrivate());
        }
        if (request.iconUrl() != null) {
            setIconUrl(request.iconUrl());
        }
        if (request.bannerUrl() != null) {
            setBannerUrl(request.bannerUrl());
        }
    }
}
