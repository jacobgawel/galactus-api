package com.galactus.thread.persistence;

import com.galactus.thread.domain.Thread;
import com.galactus.thread.dto.ThreadRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    @Query(value = """
            SELECT * FROM (
                SELECT\s
                    t.id AS id,\s
                    t.title AS title,\s
                    t.content AS content,\s
                    t.group_id AS groupId,
                    t.upvote_count AS upvoteCount,
                    t.downvote_count AS downvoteCount,
                    t.created_at AS createdAt,
                    t.updated_at AS updatedAt,
                    ROW_NUMBER() OVER (
                        PARTITION BY t.group_id
                        ORDER BY t.created_at DESC, t.id DESC        \s
                    ) AS rn
                FROM threads t      \s
                WHERE t.group_id = :groupId
            ) x
            WHERE x.rn > :offset AND x.rn <= (:offset + :limit)\s
            ORDER BY x.groupId, x.rn;
            """, nativeQuery = true)
    List<ThreadRow> findByGroupIdPaged(
            @Param("groupId") Long groupId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
