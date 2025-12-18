package com.galactus.group.application;

import com.galactus.common.constants.ContentTypePrefixes;
import com.galactus.group.domain.Group;
import com.galactus.group.dto.GroupDto;
import com.galactus.common.helpers.Base36Codec;
import com.galactus.common.mappers.GroupMapper;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.dto.GroupPatch;
import com.galactus.group.dto.UpdateGroupRequest;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.errors.SlugAlreadyTakenException;
import com.galactus.group.persistence.GroupRepository;
import com.galactus.topics.errors.TopicNotFoundException;
import com.galactus.topics.persistence.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final TopicRepository topicRepository;

    public GroupServiceImpl(GroupRepository groupRepository, TopicRepository topicRepository) {
        this.groupRepository = groupRepository;
        this.topicRepository = topicRepository;
    }

    public List<GroupDto> findAll() {
        return groupRepository.findAll()
                .stream()
                .map(GroupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDto getById(Long groupId) {
        return groupRepository.findById(groupId)
                .map(GroupMapper::toDto)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Override
    @Transactional
    public GroupDto create(@NonNull CreateGroupRequest request) {
        var entity = new Group();

        var topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new TopicNotFoundException(request.getTopicId()));

        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
        entity.setNsfw(request.isNsfw());
        entity.setPrivate(request.isPrivate());
        entity.setDisplayName(request.getDisplayName());
        entity.setTopic(topic);

        try {
            groupRepository.saveAndFlush(entity);

            entity.setHashedId(Base36Codec.generateUniqueId(ContentTypePrefixes.SPACE, entity.getId()));

            log.info("event=group.create outcome=success id={}, slug={}, hashedId={}",
                    entity.getId(), entity.getSlug(), entity.getHashedId());

            return GroupMapper.toDto(entity);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            log.warn("event=group.create outcome=conflict reason=slug_taken slug={}", request.getSlug());
            throw new SlugAlreadyTakenException(request.getSlug());
        }
    }

    @Override
    @Transactional
    public GroupDto update(UpdateGroupRequest request) {
        var entity = groupRepository.findById(request.getId())
                .orElseThrow(() -> new GroupNotFoundException(request.getId()));

        var patch = new GroupPatch(
                request.getDisplayName(),
                request.getDescription(),
                request.getNsfw(),
                request.getIsPrivate(),
                request.getIconUrl(),
                request.getBannerUrl()
        );

        entity.apply(patch);

        try {
            groupRepository.saveAndFlush(entity);

            log.info("event=group.update outcome=success group_id={} slug={}", entity.getId(), entity.getSlug());

            return GroupMapper.toDto(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            log.warn("event=group.update outcome=conflict reason=slug_taken group_id={} slug={}",
                    entity.getId(), entity.getSlug());
            throw new SlugAlreadyTakenException(entity.getSlug());
        }
    }

    @Override
    @Transactional
    public void delete(Long groupId) {
        var entity = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));

        groupRepository.delete(entity);

        log.info("event=group.delete outcome=success group_id={} slug={}", entity.getId(), entity.getSlug());
    }
}
