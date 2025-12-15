package com.galactus.persistence;

import com.galactus.domain.database.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findGroupById(Long id);
}
