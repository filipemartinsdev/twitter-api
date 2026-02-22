package com.api.twitter.user.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserAndCounts;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.exception.UserNotExistsException;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

class ListUsersUseCaseTest {
    private final UserProfile userMock1 = new UserProfile(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "desc",
            LocalDateTime.now()
    );

    private final UserAndCounts userAndCountsMock1 = new UserAndCounts() {
        @Override
        public UUID getId() {
            return userMock1.getUserId();
        }

        @Override
        public String getUsername() {
            return userMock1.getUsername();
        }

        @Override
        public String getEmail() {
            return userMock1.getEmail();
        }

        @Override
        public Long getTweetsCount() {
            return 0L;
        }

        @Override
        public Long getFollowersCount() {
            return 0L;
        }

        @Override
        public Long getFollowingCount() {
            return 0L;
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return userMock1.getCreatedAt();
        }
    };

    private UserResponse userResponseMock1 = new UserResponse(
            userMock1.getUserId(),
            userMock1.getUsername(),
            userMock1.getEmail(),
            0L,
            0L,
            0L
    );

    @Mock
    private UserProfileRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(mock != null){
            mock.close();
        }
    }

    @Test
    @DisplayName("Should return the user if it exists by userId")
    void getByIdTestCase1() {
        UserProfile expectedUser = userMock1;
        Mockito.when(userRepository.findUserAndCountsById(expectedUser.getUserId()))
                .thenReturn( Optional.of(userAndCountsMock1));

        Mockito.when(userMapper.toResponse(Mockito.any(UserAndCounts.class)))
                .thenReturn(userResponseMock1);

        UserResponse userResponse = listUsersUseCase.getById(expectedUser.getUserId());

        assertEquals(expectedUser.getUserId(),  userResponse.id());
        assertEquals(expectedUser.getUsername(), userResponse.username());
        assertEquals(expectedUser.getEmail(), userResponse.email());
    }

    @Test
    @DisplayName("Should throw UserNotExistsException when user does not exist by userId")
    void getByIdTestCase2(){
        UUID nonExistingId = UUID.randomUUID();
        Mockito.when(userRepository.findUserAndCountsById(nonExistingId))
                .thenReturn( Optional.empty());

        assertThrows(UserNotExistsException.class, () -> {
            listUsersUseCase.getById(nonExistingId);
        });
    }

    @Test
    @DisplayName("Should return a paged response of UserResponse")
    void getAllTestCase1() {
        List<UserAndCounts> userList = new ArrayList<>(
                List.of(userAndCountsMock1)
        );

        Page<UserAndCounts> page = new PageImpl<>(userList);

        Mockito.when(userRepository.findAllUserAndCounts(Mockito.any(Pageable.class)))
                .thenReturn(page);

        PagedResponse<UserResponse> expectedResponse = PagedResponse.<UserResponse>builder()
                .size(page.getSize())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLast(page.isLast())
                .content(List.of(userResponseMock1))
                .build();

        Mockito.when(userMapper.toPagedUserResponse(page))
                .thenReturn(expectedResponse);

        Pageable pageable = Mockito.mock(Pageable.class);
        PagedResponse<UserResponse> response = listUsersUseCase.getAll(pageable);

        Mockito.verify(userRepository, Mockito.times(1))
                .findAllUserAndCounts(Mockito.any());
        Mockito.verify(userMapper, Mockito.times(1))
                .toPagedUserResponse(page);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Should return a empty paged response of UserResponse when not exist users")
    void getAllTestCase2() {
        Page<UserAndCounts> emptyPage = Page.empty();
        Mockito.when(userRepository.findAllUserAndCounts(Mockito.any()))
                .thenReturn(emptyPage);
        Mockito.when(userMapper.toPagedUserResponse(emptyPage))
                .thenReturn(
                        PagedResponse.<UserResponse>builder()
                                .size(0)
                                .page(0)
                                .totalPages(0)
                                .totalElements(0L)
                                .isLast(true)
                                .content(List.of())
                                .build()
                );

        Pageable pageable = Mockito.mock(Pageable.class);
        PagedResponse<UserResponse> response = listUsersUseCase.getAll(pageable);

        Mockito.verify(userRepository, Mockito.times(1))
                .findAllUserAndCounts(Mockito.any());
        Mockito.verify(userMapper, Mockito.times(1))
                .toPagedUserResponse(emptyPage);

        assertNotNull(response);

        assertTrue(response.isLast());
        assertEquals(0, response.size());
        assertEquals(0, response.page());
        assertEquals(0, response.totalPages());
        assertEquals(0, response.totalElements());
        assertEquals(0, response.content().size());
    }

    @Test
    @DisplayName("Should return a empty paged response of UserResponse when not exist valid users")
    void queryTestCase1() {
        Mockito.when(userRepository.findAllUserAndCountsByUsernameLike(Mockito.any(), Mockito.any()))
                .thenReturn(Page.empty());

        String query = "nonExistingUser";

        Pageable pageable = Mockito.mock(Pageable.class);
        listUsersUseCase.query(query, pageable);

        // use of eq() is necessary to match the string, not object reference
        // not using eq() would cause the test to fail
        Mockito.verify(userRepository, Mockito.times(1))
                .findAllUserAndCountsByUsernameLike(eq(query), Mockito.any());
    }
}
