package com.galactus.thread.persistence;

import com.galactus.thread.domain.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
