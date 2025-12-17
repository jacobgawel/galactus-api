package com.galactus.thread.dto;

// When returned from MSSQL the columns are DateTimeOffset which means
// that we need to declare Microsoft's DateTimeOffset and the convert it
// in the service layer
import microsoft.sql.DateTimeOffset;

/**
 * Spring Data JPA interface-based projection for native SQL results.
 *
 * <p>
 * Spring generates a runtime proxy that implements this interface and backs each getter
 * with a column value from the query result set. The mapping is based on column *aliases*:
 * e.g. {@code t.hashed_id AS hashedId} must match {@code getHashedId()}.
 * </p>
 *
 * <p>
 * Keep return types aligned with what the JDBC driver produces for the database column types.
 * For SQL Server {@code datetimeoffset}, the driver commonly returns {@code microsoft.sql.DateTimeOffset}.
 * </p>
 */
public interface ThreadRow {
    Long getId();
    String getHashedId();
    String getTitle();
    String getContent();
    Long getGroupId();
    Integer getUpvoteCount();
    Integer getDownvoteCount();
    DateTimeOffset getCreatedAt();
    DateTimeOffset getUpdatedAt();
}
