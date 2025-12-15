package com.galactus.group.application;

import com.galactus.group.dto.GroupDto;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.dto.UpdateGroupRequest;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAll();
    GroupDto create(CreateGroupRequest request);
    GroupDto getById(Long groupId);
    GroupDto update(UpdateGroupRequest request);
}
