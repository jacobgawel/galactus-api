package com.galactus.thread.application;

import com.galactus.common.constants.ContentTypePrefixes;
import com.galactus.common.helpers.Base36Codec;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        threadRepository.saveAndFlush(entity);

        entity.setHashedId(Base36Codec.generateUniqueId(ContentTypePrefixes.POST, entity.getId()));

        log.info("event=thread.create outcome=success id={} group_id={} hashed_id={}",
                entity.getId(), request.getGroupId(), entity.getHashedId());

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

        log.info("event=thread.update outcome=success thread_id={}", entity.getId());

        return ThreadMapper.toDto(entity);
    }

    @Override
    public List<ThreadDto> findByGroupIdPaged(Long groupId, int limit, int offset) {
        if (!groupRepository.existsById(groupId)) {
            throw new GroupNotFoundException(groupId);
        }

        var threads = threadRepository.findByGroupIdPaged(groupId, limit, offset);

        return threads.stream()
                .map((r) -> new ThreadDto(
                        r.getId(),
                        r.getHashedId(),
                        r.getTitle(),
                        r.getContent(),
                        r.getGroupId(),
                        r.getUpvoteCount() == null ? 0 : r.getUpvoteCount(),
                        r.getDownvoteCount() == null ? 0 : r.getDownvoteCount(),
                        r.getCreatedAt().getTimestamp().toInstant(),
                        r.getUpdatedAt().getTimestamp().toInstant()
                )).collect(Collectors.toList());
    }
}
