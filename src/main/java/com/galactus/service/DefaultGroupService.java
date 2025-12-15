package com.galactus.service;

import com.galactus.domain.constants.ContentTypePrefixes;
import com.galactus.domain.database.Group;
import com.galactus.domain.dto.GroupDto;
import com.galactus.domain.helpers.Base36Codec;
import com.galactus.domain.interfaces.GroupService;
import com.galactus.domain.mappers.GroupMapper;
import com.galactus.domain.models.CreateGroupRequest;
import com.galactus.persistence.GroupRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DefaultGroupService implements GroupService {
    private final GroupRepository repository;

    public DefaultGroupService(GroupRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GroupDto create(@NonNull CreateGroupRequest request) {
        var entity = new Group();

        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
        entity.setNsfw(request.isNsfw());
        entity.setPrivate(request.isPrivate());
        entity.setDisplayName(request.getDisplayName());

        repository.saveAndFlush(entity);

        var hashedId = Base36Codec.generateUniqueId(ContentTypePrefixes.space, entity.getId());

        entity.setHashedId(hashedId);

        repository.save(entity);

        return GroupMapper.toResponse(entity);
    }

    @Override
    public Optional<GroupDto> getById(Long groupId) {
        return repository.findById(groupId).map(GroupMapper::toResponse);
    }

    public List<GroupDto> findAll() {
        return repository.findAll()
                .stream()
                .map(GroupMapper::toResponse)
                .collect(Collectors.toList());
    }
}
