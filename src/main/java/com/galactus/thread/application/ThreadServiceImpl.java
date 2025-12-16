package com.galactus.thread.application;

import com.galactus.common.mappers.ThreadMapper;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.persistence.GroupRepository;
import com.galactus.thread.domain.Thread;
import com.galactus.thread.dto.CreateThreadRequest;
import com.galactus.thread.dto.ThreadDto;
import com.galactus.thread.dto.UpdateThreadRequest;
import com.galactus.thread.errors.ThreadNotFoundException;
import com.galactus.thread.persistence.ThreadRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ThreadServiceImpl implements ThreadService {
    private final ThreadRepository threadRepository;
    private final GroupRepository groupRepository;

    public ThreadServiceImpl(ThreadRepository repository, GroupRepository groupRepository) {
        this.threadRepository = repository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<ThreadDto> findAll() {
        return threadRepository.findAll()
                .stream()
                .map(ThreadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ThreadDto create(@NotNull CreateThreadRequest request) {
        var entity = new Thread();

        var group = groupRepository.findById(request.groupId)
                .orElseThrow(() -> new GroupNotFoundException(request.groupId));

        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setGroup(group);

        threadRepository.save(entity);

        return ThreadMapper.toDto(entity);
    }

    @Override
    public ThreadDto getById(Long id) {
        return threadRepository.findById(id)
                .map(ThreadMapper::toDto)
                .orElseThrow(() -> new ThreadNotFoundException(id));
    }

    @Override
    @Transactional
    public ThreadDto update(UpdateThreadRequest request) {
        var entity = threadRepository.findById(request.getId())
                .orElseThrow(() -> new ThreadNotFoundException(request.getId()));

        // might not need an abstraction over here since
        // at this time only updating 2 props
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            entity.setContent(request.getContent());
        }

        return ThreadMapper.toDto(entity);
    }
}
