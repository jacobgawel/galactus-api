package com.galactus.thread.dto;

// When returned from MSSQL the columns are DateTimeOffset which means
// that we need to declare Microsoft's DateTimeOffset and the convert it
// in the service layer
import microsoft.sql.DateTimeOffset;

public interface ThreadRow {
    Long getId();
    String getTitle();
    String getContent();
    Long getGroupId();
    Integer getUpvoteCount();
    Integer getDownvoteCount();
    DateTimeOffset getCreatedAt();
    DateTimeOffset getUpdatedAt();
}

