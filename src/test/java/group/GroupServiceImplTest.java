package group;

import com.galactus.common.constants.ContentTypePrefixes;
import com.galactus.group.application.GroupServiceImpl;
import com.galactus.group.domain.Group;
import com.galactus.group.dto.CreateGroupRequest;
import com.galactus.group.dto.GroupDto;
import com.galactus.group.dto.UpdateGroupRequest;
import com.galactus.group.errors.GroupNotFoundException;
import com.galactus.group.errors.SlugAlreadyTakenException;
import com.galactus.group.persistence.GroupRepository;
import com.galactus.topics.domain.Topic;
import com.galactus.topics.errors.TopicNotFoundException;
import com.galactus.topics.persistence.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GroupServiceImpl Tests")
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Captor
    private ArgumentCaptor<Group> groupCaptor;

    private Group testGroup;
    private Topic testTopic;
    private CreateGroupRequest createRequest;
    private UpdateGroupRequest updateRequest;

    @BeforeEach
    void setUp() {
        // Setup test topic
        testTopic = new Topic();
        testTopic.setId(1);
        testTopic.setDisplayName("Gaming");
        testTopic.setEmoji("🎮");
        testTopic.setCreatedAt(Instant.now());
        testTopic.setUpdatedAt(Instant.now());

        // Setup test group
        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setHashedId("sp_rs1c1");
        testGroup.setSlug("gaming-discussion");
        testGroup.setDisplayName("Gaming Discussion");
        testGroup.setDescription("A place to discuss all things gaming");
        testGroup.setNsfw(false);
        testGroup.setPrivate(false);
        testGroup.setTopic(testTopic);
        testGroup.setIconUrl("https://example.com/icon.png");
        testGroup.setBannerUrl("https://example.com/banner.png");
        testGroup.setCreatedAt(Instant.now());
        testGroup.setUpdatedAt(Instant.now());

        // Setup create request
        createRequest = new CreateGroupRequest();
        createRequest.setSlug("gaming-discussion");
        createRequest.setDisplayName("Gaming Discussion");
        createRequest.setDescription("A place to discuss all things gaming");
        createRequest.setTopicId(1);
        createRequest.setNsfw(false);
        createRequest.setPrivate(false);

        // Setup update request
        updateRequest = new UpdateGroupRequest();
        updateRequest.setId(1L);
        updateRequest.setDisplayName("Updated Gaming Discussion");
        updateRequest.setDescription("Updated description");
        updateRequest.setNsfw(false);
        updateRequest.setIsPrivate(true);
    }

    @Test
    @DisplayName("findAll should return all groups as DTOs")
    void testFindAll_ReturnsAllGroups() {
        // Arrange
        Group group2 = new Group();
        group2.setId(2L);
        group2.setHashedId("sp_rs1c2");
        group2.setSlug("tech-news");
        group2.setDisplayName("Tech News");
        group2.setDescription("Latest technology news");
        group2.setTopic(testTopic);
        group2.setNsfw(false);
        group2.setPrivate(false);
        group2.setCreatedAt(Instant.now());
        group2.setUpdatedAt(Instant.now());

        List<Group> groups = Arrays.asList(testGroup, group2);
        when(groupRepository.findAll()).thenReturn(groups);

        // Act
        List<GroupDto> result = groupService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("gaming-discussion", result.get(0).slug());
        assertEquals("tech-news", result.get(1).slug());
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll should return empty list when no groups exist")
    void testFindAll_ReturnsEmptyList() {
        // Arrange
        when(groupRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<GroupDto> result = groupService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getById should return group when found")
    void testGetById_GroupExists_ReturnsGroupDto() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));

        // Act
        GroupDto result = groupService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("gaming-discussion", result.slug());
        assertEquals("Gaming Discussion", result.displayName());
        verify(groupRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById should throw GroupNotFoundException when group not found")
    void testGetById_GroupNotFound_ThrowsException() {
        // Arrange
        when(groupRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        GroupNotFoundException exception = assertThrows(
                GroupNotFoundException.class,
                () -> groupService.getById(999L)
        );

        assertTrue(exception.getMessage().contains("999"));
        verify(groupRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create should successfully create a new group")
    void testCreate_ValidRequest_CreatesGroup() {
        // Arrange
        when(topicRepository.findById(1)).thenReturn(Optional.of(testTopic));
        when(groupRepository.saveAndFlush(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });

        // Act
        GroupDto result = groupService.create(createRequest);

        // Assert
        assertNotNull(result);
        verify(topicRepository, times(1)).findById(1);
        verify(groupRepository, times(1)).saveAndFlush(any(Group.class));

        // Verify the group was created with correct properties
        verify(groupRepository).saveAndFlush(groupCaptor.capture());
        Group capturedGroup = groupCaptor.getValue();
        assertEquals("gaming-discussion", capturedGroup.getSlug());
        assertEquals("Gaming Discussion", capturedGroup.getDisplayName());
        assertEquals("A place to discuss all things gaming", capturedGroup.getDescription());
        assertFalse(capturedGroup.isNsfw());
        assertFalse(capturedGroup.isPrivate());
        assertEquals(testTopic, capturedGroup.getTopic());
    }

    @Test
    @DisplayName("create should throw TopicNotFoundException when topic does not exist")
    void testCreate_TopicNotFound_ThrowsException() {
        // Arrange
        when(topicRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        TopicNotFoundException exception = assertThrows(
                TopicNotFoundException.class,
                () -> groupService.create(createRequest)
        );

        verify(topicRepository, times(1)).findById(1);
        verify(groupRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("create should throw SlugAlreadyTakenException when slug is duplicate")
    void testCreate_DuplicateSlug_ThrowsException() {
        // Arrange
        when(topicRepository.findById(1)).thenReturn(Optional.of(testTopic));
        when(groupRepository.saveAndFlush(any(Group.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate slug"));

        // Act & Assert
        SlugAlreadyTakenException exception = assertThrows(
                SlugAlreadyTakenException.class,
                () -> groupService.create(createRequest)
        );

        assertTrue(exception.getMessage().contains("gaming-discussion"));
        verify(topicRepository, times(1)).findById(1);
        verify(groupRepository, times(1)).saveAndFlush(any(Group.class));
    }

    @Test
    @DisplayName("create should generate hashedId after save")
    void testCreate_GeneratesHashedId() {
        // Arrange
        when(topicRepository.findById(1)).thenReturn(Optional.of(testTopic));
        when(groupRepository.saveAndFlush(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });

        // Act
        groupService.create(createRequest);

        // Assert
        verify(groupRepository).saveAndFlush(groupCaptor.capture());
        Group capturedGroup = groupCaptor.getValue();
        
        // Verify hashedId is set
        assertNotNull(capturedGroup.getHashedId());
        assertTrue(capturedGroup.getHashedId().startsWith(ContentTypePrefixes.SPACE));
    }

    @Test
    @DisplayName("update should successfully update an existing group")
    void testUpdate_ValidRequest_UpdatesGroup() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(groupRepository.saveAndFlush(any(Group.class))).thenReturn(testGroup);

        // Act
        GroupDto result = groupService.update(updateRequest);

        // Assert
        assertNotNull(result);
        verify(groupRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).saveAndFlush(testGroup);
        
        // Verify the group properties were updated
        assertEquals("Updated Gaming Discussion", testGroup.getDisplayName());
        assertEquals("Updated description", testGroup.getDescription());
        assertTrue(testGroup.isPrivate());
    }

    @Test
    @DisplayName("update should throw GroupNotFoundException when group not found")
    void testUpdate_GroupNotFound_ThrowsException() {
        // Arrange
        when(groupRepository.findById(999L)).thenReturn(Optional.empty());
        updateRequest.setId(999L);

        // Act & Assert
        GroupNotFoundException exception = assertThrows(
                GroupNotFoundException.class,
                () -> groupService.update(updateRequest)
        );

        assertTrue(exception.getMessage().contains("999"));
        verify(groupRepository, times(1)).findById(999L);
        verify(groupRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("update should throw SlugAlreadyTakenException when data integrity violated")
    void testUpdate_DataIntegrityViolation_ThrowsException() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(groupRepository.saveAndFlush(any(Group.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate slug"));

        // Act & Assert
        SlugAlreadyTakenException exception = assertThrows(
                SlugAlreadyTakenException.class,
                () -> groupService.update(updateRequest)
        );

        verify(groupRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).saveAndFlush(any(Group.class));
    }

    @Test
    @DisplayName("update should only update non-null fields")
    void testUpdate_PartialUpdate_OnlyUpdatesNonNullFields() {
        // Arrange
        UpdateGroupRequest partialUpdate = new UpdateGroupRequest();
        partialUpdate.setId(1L);
        partialUpdate.setDisplayName("New Display Name");
        // description, nsfw, isPrivate, iconUrl, bannerUrl are all null

        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(groupRepository.saveAndFlush(any(Group.class))).thenReturn(testGroup);

        String originalDescription = testGroup.getDescription();
        boolean originalNsfw = testGroup.isNsfw();
        boolean originalPrivate = testGroup.isPrivate();

        // Act
        groupService.update(partialUpdate);

        // Assert
        assertEquals("New Display Name", testGroup.getDisplayName());
        assertEquals(originalDescription, testGroup.getDescription());
        assertEquals(originalNsfw, testGroup.isNsfw());
        assertEquals(originalPrivate, testGroup.isPrivate());
    }

    @Test
    @DisplayName("delete should successfully delete an existing group")
    void testDelete_GroupExists_DeletesGroup() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        doNothing().when(groupRepository).delete(testGroup);

        // Act
        groupService.delete(1L);

        // Assert
        verify(groupRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).delete(testGroup);
    }

    @Test
    @DisplayName("delete should throw GroupNotFoundException when group not found")
    void testDelete_GroupNotFound_ThrowsException() {
        // Arrange
        when(groupRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        GroupNotFoundException exception = assertThrows(
                GroupNotFoundException.class,
                () -> groupService.delete(999L)
        );

        assertTrue(exception.getMessage().contains("999"));
        verify(groupRepository, times(1)).findById(999L);
        verify(groupRepository, never()).delete(any());
    }

    @Test
    @DisplayName("create should handle NSFW and private flags correctly")
    void testCreate_WithNsfwAndPrivateFlags_CreatesCorrectly() {
        // Arrange
        createRequest.setNsfw(true);
        createRequest.setPrivate(true);

        when(topicRepository.findById(1)).thenReturn(Optional.of(testTopic));
        when(groupRepository.saveAndFlush(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });

        // Act
        groupService.create(createRequest);

        // Assert
        verify(groupRepository).saveAndFlush(groupCaptor.capture());
        Group capturedGroup = groupCaptor.getValue();
        assertTrue(capturedGroup.isNsfw());
        assertTrue(capturedGroup.isPrivate());
    }

    @Test
    @DisplayName("update should handle icon and banner URLs")
    void testUpdate_WithIconAndBannerUrls_UpdatesCorrectly() {
        // Arrange
        updateRequest.setIconUrl("https://example.com/new-icon.png");
        updateRequest.setBannerUrl("https://example.com/new-banner.png");

        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(groupRepository.saveAndFlush(any(Group.class))).thenReturn(testGroup);

        // Act
        groupService.update(updateRequest);

        // Assert
        assertEquals("https://example.com/new-icon.png", testGroup.getIconUrl());
        assertEquals("https://example.com/new-banner.png", testGroup.getBannerUrl());
        verify(groupRepository, times(1)).saveAndFlush(testGroup);
    }
}
