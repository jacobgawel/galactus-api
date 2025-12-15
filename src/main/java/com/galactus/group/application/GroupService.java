package com.galactus.group.application;

import com.galactus.group.dto.GroupDto;
import com.galactus.group.dto.CreateGroupRequest;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupDto> findAll();
    GroupDto create(CreateGroupRequest request);
    GroupDto getById(Long groupId);
}
