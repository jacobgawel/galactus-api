package com.galactus.topics.domain;

import com.galactus.group.domain.Group;
import jakarta.persistence.*;
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
        name = "topics"
)
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Size(min = 2, max = 50)
    private String displayName;

    private String emoji;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (displayName != null) {
            displayName = displayName.trim();
        }
    }

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
