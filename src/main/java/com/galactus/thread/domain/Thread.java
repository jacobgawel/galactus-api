package com.galactus.thread.domain;

import com.galactus.group.domain.Group;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "threads"
)
@Getter
@Setter
public class Thread {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    @Size(min = 2, max = 300)
    private String title;

    @Size(max = 40_000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_threads_group")
    )
    private Group group;

    // NOTE: temporarily using integers until I configure authorization
    // the real solution should be connected to users
    private Integer upvoteCount;
    private Integer downvoteCount;
    // counter above will be there to prevent re-counting votes on every page load
    // values will be incremented during the same db transaction to keep strong consistency
    // ----- rough idea ----
    // upvote -> inserts row -> updates to +1
    // downvote -> deletes row -> updates to -1

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
