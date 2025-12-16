package com.galactus.thread.application;

import com.galactus.thread.dto.CreateThreadRequest;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.thread.dto.UpdateThreadRequest;

import java.util.List;

public interface ThreadService {
    List<ThreadDto> findAll();
    ThreadDto create(CreateThreadRequest request);
    ThreadDto getById(Long id);
    ThreadDto update(UpdateThreadRequest request);
    List<ThreadDto> findByGroupIdPaged(Long groupId , int limit, int offset);
}
