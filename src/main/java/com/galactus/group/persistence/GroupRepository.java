package com.galactus.group.persistence;

import com.galactus.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsBySlug(String slug);
}
