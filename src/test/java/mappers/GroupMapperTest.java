package mappers;

import com.galactus.common.mappers.GroupMapper;
import com.galactus.group.dto.GroupDto;
import com.galactus.topics.domain.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.galactus.group.domain.Group;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class GroupMapperTest {
    private Group testGroup;
    private Instant testCreatedAt;
    private Instant testUpdatedAt;

    @BeforeEach
    void setUp() {
        testCreatedAt = Instant.parse("2024-01-15T10:30:00Z");
        testUpdatedAt = Instant.parse("2024-01-20T15:45:00Z");

        // Setup test topic
        Topic testTopic = new Topic();
        testTopic.setId(1);
        testTopic.setDisplayName("Gaming");
        testTopic.setEmoji("🎮");
        testTopic.setCreatedAt(testCreatedAt);
        testTopic.setUpdatedAt(testUpdatedAt);

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
        testGroup.setCreatedAt(testCreatedAt);
        testGroup.setUpdatedAt(testUpdatedAt);
    }

    @Test
    @DisplayName("toDto should map all fields correctly from Group entity to GroupDto")
    void testToDto_ValidGroup_MapsAllFieldsCorrectly() {
        // Act
        GroupDto result = GroupMapper.toDto(testGroup);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("sp_rs1c1", result.hashedId());
        assertEquals("gaming-discussion", result.slug());
        assertEquals("Gaming Discussion", result.displayName());
        assertEquals("A place to discuss all things gaming", result.description());
        assertEquals("https://example.com/icon.png", result.iconUrl());
        assertEquals("https://example.com/banner.png", result.bannerUrl());
        assertEquals(1, result.topicId());
        assertFalse(result.nsfw());
        assertFalse(result.isPrivate());
        assertEquals(testCreatedAt, result.createdAt());
        assertEquals(testUpdatedAt, result.updatedAt());
    }

    @Test
    @DisplayName("toDto should handle null entity and return null")
    void testToDto_NullEntity_ReturnsNull() {
        // Act
        GroupDto result = GroupMapper.toDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("toDto should map group with nsfw=true correctly")
    void testToDto_NsfwGroup_MapsCorrectly() {
        // Arrange
        testGroup.setNsfw(true);

        // Act
        GroupDto result = GroupMapper.toDto(testGroup);

        // Assert
        assertNotNull(result);
        assertTrue(result.nsfw());
    }

    @Test
    @DisplayName("toDto should map group with isPrivate=true correctly")
    void testToDto_PrivateGroup_MapsCorrectly() {
        // Arrange
        testGroup.setPrivate(true);

        // Act
        GroupDto result = GroupMapper.toDto(testGroup);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPrivate());
    }

    @Test
    @DisplayName("toDto should handle null optional fields")
    void testToDto_NullOptionalFields_MapsCorrectly() {
        // Arrange
        testGroup.setDescription(null);
        testGroup.setIconUrl(null);
        testGroup.setBannerUrl(null);

        // Act
        GroupDto result = GroupMapper.toDto(testGroup);

        // Assert
        assertNotNull(result);
        assertNull(result.description());
        assertNull(result.iconUrl());
        assertNull(result.bannerUrl());
        // Required fields should still be present
        assertEquals(1L, result.id());
        assertEquals("gaming-discussion", result.slug());
    }

    @Test
    @DisplayName("toDto should extract topicId from related Topic entity")
    void testToDto_ExtractsTopicIdCorrectly() {
        // Arrange
        Topic newTopic = new Topic();
        newTopic.setId(42);
        testGroup.setTopic(newTopic);

        // Act
        GroupDto result = GroupMapper.toDto(testGroup);

        // Assert
        assertNotNull(result);
        assertEquals(42, result.topicId());
    }
}
