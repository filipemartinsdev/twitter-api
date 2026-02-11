package com.api.twitter.user.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.dto.UserAndCounts;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.exception.UserNotExists;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

class ListUsersUseCaseTest {
    private final User userMock1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "12345",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final User userMock2 = new User(
            UUID.randomUUID(),
            "test2",
            "test2@gmail.com",
            "12345",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final UserAndCounts userAndCountsMock1 = new UserAndCounts() {
        @Override
        public UUID getId() {
            return userMock1.getId();
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
            userMock1.getId(),
            userMock1.getUsername(),
            userMock1.getEmail(),
            0L,
            0L,
            0L
    );

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(mock != null){
            mock.close();
        }
    }

    @Test
    @DisplayName("Should return the user if it exists by id")
    void getByIdTestCase1() {
        User expectedUser = userMock1;
        Mockito.when(userRepository.findById(expectedUser.getId()))
                .thenReturn( Optional.of(userMock1));
        Mockito.when(userRepository.existsById(expectedUser.getId()))
                .thenReturn( true);
        Mockito.when(userRepository.findUserAndCountsById(expectedUser.getId()))
                .thenReturn( Optional.of(userAndCountsMock1));

        Mockito.when(userMapper.toResponse(Mockito.any(UserAndCounts.class)))
                .thenReturn(userResponseMock1);

        UserResponse userResponse = listUsersUseCase.getById(expectedUser.getId());

        assertEquals(expectedUser.getId(),  userResponse.id());
        assertEquals(expectedUser.getUsername(), userResponse.username());
        assertEquals(expectedUser.getEmail(), userResponse.email());
    }

    @Test
    @DisplayName("Should throw UserNotExists when user does not exist by id")
    void getByIdTestCase2(){
        UUID nonExistingId = UUID.randomUUID();
        Mockito.when(userRepository.findById(nonExistingId))
                .thenReturn( Optional.empty());
        Mockito.when(userRepository.existsById(nonExistingId))
                .thenReturn( false);
        Mockito.when(userRepository.findUserAndCountsById(nonExistingId))
                .thenReturn( Optional.empty());

        assertThrows(UserNotExists.class, () -> {
            listUsersUseCase.getById(nonExistingId);
        });
    }

    @Test
    @DisplayName("Should return a paged response of UserResponse")
    void getAllTestCase1() {
        List<UserAndCounts> userList = new ArrayList<>(
                List.of(userAndCountsMock1)
        );

        List<UserResponse> userResponseList = new ArrayList<>(
                List.of(userResponseMock1)
        );

        PagedResponse<UserResponse> expectedResponse = PagedResponse.<UserResponse>builder()
                .size(1)
                .page(1)
                .totalPages(1)
                .totalElements(1L)
                .isLast(true)
                .content(userResponseList)
                .build();

        Page<UserAndCounts> page = new Page<UserAndCounts>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super UserAndCounts, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<UserAndCounts> getContent() {
                return userList;
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return true;
            }

            @Override
            public boolean isLast() {
                return true;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<UserAndCounts> iterator() {
                return null;
            }
        };

        Mockito.when(userRepository.findAllUserAndCounts(Mockito.any(Pageable.class)))
                .thenReturn(page);

        Pageable pageable = Mockito.mock(Pageable.class);
        listUsersUseCase.getAll(pageable);

        Mockito.verify(userRepository, Mockito.times(1))
                .findAllUserAndCounts(Mockito.any());
    }

    @Test
    @DisplayName("Should return a empty paged response of UserResponse when not exist users")
    void getAllTestCase2() {
        Mockito.when(userRepository.findAllUserAndCounts(Mockito.any()))
                .thenReturn( Page.empty());
        Mockito.when(userMapper.toPagedUserResponse(Page.empty()))
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
        Mockito.when(userRepository.findAllUserAndCounts(Mockito.any()))
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