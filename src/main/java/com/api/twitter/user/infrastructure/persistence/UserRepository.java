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
                   user.username AS username,
                   user.email AS email, 
                   (
                     SELECT COUNT(t)
                     FROM Tweet t
                     WHERE t.user.id = :userId
                   ) AS tweetsCount,
                   (
                       SELECT COUNT(ur)
                       FROM UserRelationship ur
                       WHERE ur.following.id = :userId
                   ) AS followersCount,
                   (
                       SELECT COUNT(ur)
                       FROM UserRelationship ur
                       WHERE ur.follower.id = :userId                
                   ) AS followingCount
            FROM User user
            WHERE user.id = :userId
    """)
    Optional<UserAndCounts> findUserAndCountsById(@Param("userId") UUID userId);

    @Query("""
           SELECT user.id AS id,
                  user.username as username,
                  user.email AS email,
                  (
                     SELECT COUNT(t)
                     FROM Tweet t
                     WHERE t.user.id = user.id
                  ) AS tweetsCount,
                  (
                      SELECT COUNT(ur)
                      FROM UserRelationship ur
                      WHERE ur.following.id = user.id
                  ) AS followersCount,
                  (
                      SELECT COUNT(ur)
                      FROM UserRelationship ur
                      WHERE ur.follower.id = user.id
                  ) AS followingCount
           FROM User user
           WHERE user.username LIKE CONCAT('%', :username, '%')
    """)
    Page<UserAndCounts> findAllUserAndCountsByUsernameLike(@Param("username") String username, Pageable pageable);

    @Query("""
           SELECT user.id AS id,
                  user.username as username,
                  user.email AS email,
                  (
                     SELECT COUNT(t)
                     FROM Tweet t
                     WHERE t.user.id = user.id
                  ) AS tweetsCount,
                  (
                      SELECT COUNT(ur)
                      FROM UserRelationship ur
                      WHERE ur.following.id = user.id
                  ) AS followersCount,
                  (
                      SELECT COUNT(ur)
                      FROM UserRelationship ur
                      WHERE ur.follower.id = user.id
                  ) AS followingCount
           FROM User user
    """)
    Page<UserAndCounts> findAllUserAndCounts(Pageable pageable);

    /**
     * Strict use to Authentication
     */
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

}
