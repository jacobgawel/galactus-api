package com.galactus.domain.interfaces;

import com.galactus.domain.dto.GroupDto;
import com.galactus.domain.models.CreateGroupRequest;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupDto> findAll();
    GroupDto create(CreateGroupRequest request);
    Optional<GroupDto> getById(Long groupId);
}
