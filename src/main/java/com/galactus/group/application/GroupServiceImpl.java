package com.galactus.group.application;

import com.galactus.common.constants.ContentTypePrefixes;
import com.galactus.group.domain.Group;
import com.galactus.group.dto.GroupDto;
import com.galactus.common.helpers.Base36Codec;
import com.galactus.common.mappers.GroupMapper;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.errors.SlugAlreadyTakenException;
import com.galactus.group.persistence.GroupRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GroupDto create(@NonNull CreateGroupRequest request) {

        var exists = repository.existsBySlug(request.slug);

        if (exists) {
            throw new SlugAlreadyTakenException(request.slug);
        }

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
    public GroupDto getById(Long groupId) {
        return repository.findById(groupId)
                .map(GroupMapper::toResponse)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public List<GroupDto> findAll() {
        return repository.findAll()
                .stream()
                .map(GroupMapper::toResponse)
                .collect(Collectors.toList());
    }
}
