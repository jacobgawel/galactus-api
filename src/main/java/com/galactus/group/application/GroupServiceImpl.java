package com.galactus.group.application;

import com.galactus.common.constants.ContentTypePrefixes;
import com.galactus.group.domain.Group;
import com.galactus.group.dto.GroupDto;
import com.galactus.common.helpers.Base36Codec;
import com.galactus.common.mappers.GroupMapper;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.dto.UpdateGroupRequest;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.errors.SlugAlreadyTakenException;
import com.galactus.group.persistence.GroupRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public GroupDto create(@NonNull CreateGroupRequest request) {
        var entity = new Group();
        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
        entity.setNsfw(request.isNsfw());
        entity.setPrivate(request.isPrivate());
        entity.setDisplayName(request.getDisplayName());

        try {
            repository.save(entity);
            repository.flush();

            entity.setHashedId(Base36Codec.generateUniqueId(ContentTypePrefixes.SPACE, entity.getId()));

            return GroupMapper.toResponse(entity);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new SlugAlreadyTakenException(request.slug);
        }
    }

    @Override
    public GroupDto getById(Long groupId) {
        return repository.findById(groupId)
                .map(GroupMapper::toResponse)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Override
    @Transactional
    public GroupDto update(UpdateGroupRequest request) {
        var entity = repository.findById(request.getId())
                .orElseThrow(() -> new GroupNotFoundException(request.getId()));

        if (request.getDisplayName() != null) {
            entity.setDisplayName(request.getDisplayName());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.nsfw != null) {
            entity.setNsfw(request.getNsfw());
        }
        if (request.isPrivate != null) {
            entity.setPrivate(request.getIsPrivate());
        }
        if (request.getIconUrl() != null) {
            entity.setIconUrl(request.getIconUrl());
        }
        if (request.getBannerUrl() != null) {
            entity.setBannerUrl(request.getBannerUrl());
        }

        try {
            repository.saveAndFlush(entity);
            return GroupMapper.toResponse(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new SlugAlreadyTakenException(entity.getSlug());
        }
    }

    public List<GroupDto> findAll() {
        return repository.findAll()
                .stream()
                .map(GroupMapper::toResponse)
                .collect(Collectors.toList());
    }
}
