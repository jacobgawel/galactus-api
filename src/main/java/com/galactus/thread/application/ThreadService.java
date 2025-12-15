package com.galactus.thread.application;

import com.galactus.thread.dto.ThreadDto;

import java.util.List;

public interface ThreadService {
    List<ThreadDto> findAll();
    ThreadDto create();
    ThreadDto getById(Long id);
    ThreadDto update();
}
