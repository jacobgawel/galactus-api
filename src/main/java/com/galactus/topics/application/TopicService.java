package com.galactus.topics.application;

import com.galactus.topics.dto.CreateTopicRequest;
import com.galactus.topics.dto.TopicDto;

import java.util.List;

public interface TopicService {
    List<TopicDto> findAll();
    TopicDto create(CreateTopicRequest request);
    TopicDto getById(Integer topicId);
    TopicDto update();
}
