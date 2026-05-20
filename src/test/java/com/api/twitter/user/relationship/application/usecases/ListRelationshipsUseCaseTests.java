package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRelationshipsUseCaseTests {
    @Mock private UserRelationshipRepository userRelationshipRepository;
    @Mock private UserMapper userMapper;
    @Mock private VerifyUserUseCase verifyUserUseCase;

    @InjectMocks private ListRelationshipsUseCase listRelationshipsUseCase;

    @Test @DisplayName("Should return followers of user")
    void getAllFollowersOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("user");

        Page<UserProfile> page = new PageImpl<>(List.of(userProfile), pageable, 1);
        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .username("username")
                .email("email@test.com")
                .tweetsCount(0L)
                .followersCount(0L)
                .followingCount(0L)
                .build();

        when(verifyUserUseCase.existsById(userId)).thenReturn(true);
        when(userRelationshipRepository.findAllFollowersOfUser(userId, pageable)).thenReturn(page);
        when(userMapper.toResponse(any(UserProfile.class))).thenReturn(userResponse);

        // When
        PagedResponse<UserResponse> result = listRelationshipsUseCase.getAllFollowersOfUser(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(verifyUserUseCase).existsById(userId);
        verify(userRelationshipRepository).findAllFollowersOfUser(userId, pageable);
    }

    @Test @DisplayName("Should throw BadRequestException when user not found for followers")
    void getAllFollowersOfUserTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        when(verifyUserUseCase.existsById(userId)).thenReturn(false);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            listRelationshipsUseCase.getAllFollowersOfUser(userId, pageable);
        });
    }

    @Test @DisplayName("Should return following of user")
    void getAllFollowingOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("user");

        Page<UserProfile> page = new PageImpl<>(List.of(userProfile), pageable, 1);
        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .username("username")
                .email("email@test.com")
                .tweetsCount(0L)
                .followersCount(0L)
                .followingCount(0L)
                .build();

        when(userRelationshipRepository.findAllFollowingOfUser(userId, pageable)).thenReturn(page);
        when(userMapper.toResponse(any(UserProfile.class))).thenReturn(userResponse);

        // When
        PagedResponse<UserResponse> result = listRelationshipsUseCase.getAllFollowingOfUser(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(userRelationshipRepository).findAllFollowingOfUser(userId, pageable);
    }
}