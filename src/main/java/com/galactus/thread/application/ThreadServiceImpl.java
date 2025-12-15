package com.galactus.thread.application;

import com.galactus.common.mappers.ThreadMapper;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.thread.persistence.ThreadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ThreadServiceImpl implements ThreadService {
    private final ThreadRepository repository;

    public ThreadServiceImpl(ThreadRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ThreadDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ThreadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThreadDto create() {
        return null;
    }

    @Override
    public ThreadDto getById(Long id) {
        return null;
    }

    @Override
    public ThreadDto update() {
        return null;
    }
}
