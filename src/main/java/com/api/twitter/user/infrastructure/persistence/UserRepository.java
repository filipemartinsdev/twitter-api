package com.api.twitter.user.infrastructure.persistence;

import com.api.twitter.user.application.dto.UserAndCounts;
import com.api.twitter.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

// TODO: fix N+1 problem
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
            SELECT user.id AS id,
                  user.username as username,
                  user.email AS email,
                  user.createdAt AS createdAt,
                  COUNT(DISTINCT t) AS tweetsCount,
                  COUNT(DISTINCT ur1) AS followersCount,
                  COUNT(DISTINCT ur2) AS followingCount
           FROM User user
           
           LEFT JOIN Tweet t
           ON user.id = t.user.id
           LEFT JOIN UserRelationship ur1
           ON user.id = ur1.following.id
           LEFT JOIN UserRelationship ur2
           ON user.id = ur2.follower.id
           WHERE user.id = :userId
           GROUP BY user.id           
    """)
    Optional<UserAndCounts> findUserAndCountsById(@Param("userId") UUID userId);

    @Query("""
           SELECT user.id AS id,
                  user.username as username,
                  user.email AS email,
                  user.createdAt AS createdAt,
                  COUNT(DISTINCT t) AS tweetsCount,                  
                  COUNT(DISTINCT ur1) AS followersCount,
                  COUNT(DISTINCT ur2) AS followingCount
           FROM User user
           
           LEFT JOIN Tweet t
           ON user.id = t.user.id
           LEFT JOIN UserRelationship ur1
           ON user.id = ur1.following.id
           LEFT JOIN UserRelationship ur2
           ON user.id = ur2.follower.id
           WHERE user.username LIKE CONCAT('%', :username, '%')
           GROUP BY user.id           
    """)
    Page<UserAndCounts> findAllUserAndCountsByUsernameLike(@Param("username") String username, Pageable pageable);

    @Query("""
           SELECT user.id AS id,
                  user.username as username,
                  user.email AS email,
                  user.createdAt AS createdAt,
                  COUNT(DISTINCT t) AS tweetsCount,
                  COUNT(DISTINCT ur1) AS followersCount,
                  COUNT(DISTINCT ur2) AS followingCount
           FROM User user
           
           LEFT JOIN Tweet t
           ON user.id = t.user.id
           LEFT JOIN UserRelationship ur1
           ON user.id = ur1.following.id
           LEFT JOIN UserRelationship ur2
           ON user.id = ur2.follower.id
           GROUP BY user.id
    """)
    Page<UserAndCounts> findAllUserAndCounts(Pageable pageable);

    /**
     * Strict use to Authentication
     */
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

}
