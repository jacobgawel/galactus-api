package com.galactus.topics.application;

import com.galactus.common.mappers.TopicMapper;
import com.galactus.topics.domain.Topic;
import com.galactus.topics.dto.CreateTopicRequest;
import com.galactus.topics.dto.TopicDto;
import com.galactus.topics.errors.TopicNotFoundException;
import com.galactus.topics.persistence.TopicRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TopicServiceImpl implements TopicService {
    private final TopicRepository repository;

    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TopicDto> findAll() {
        return repository.findAll()
                .stream()
                .map(TopicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TopicDto getById(Integer topicId) {
        return repository.findById(topicId)
                .map(TopicMapper::toDto)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
    }

    @Override
    public TopicDto create(@NotNull CreateTopicRequest request) {
        var entity = new Topic();
        entity.setDisplayName(request.getDisplayName());

        if (request.getEmoji() != null) {
            entity.setEmoji(request.getEmoji());
        }

        repository.saveAndFlush(entity);

        log.info("event=topic.create outcome=success id={} displayName={}",
                entity.getId(), entity.getDisplayName());

        return TopicMapper.toDto(entity);
    }

    @Override
    public TopicDto update() {
        return null;
    }
}
