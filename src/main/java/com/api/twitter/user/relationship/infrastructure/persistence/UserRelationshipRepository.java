package com.api.twitter.user.relationship.infrastructure.persistence;

import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.relationship.domain.UserRelationship;
import com.api.twitter.user.relationship.domain.UserRelationshipId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UserRelationshipId> {
    @Query(
            "SELECT COUNT(ur) FROM UserRelationship ur "+
            "WHERE ur.following.userId = :userId"
    )
    long getFollowersCountOfUser(@Param("userId") UUID userId);

    @Query(
            "SELECT COUNT(ur) FROM UserRelationship ur "+
            "WHERE ur.follower.userId = :userId"
    )
    long getFollowingCountOfUser(@Param("userId") UUID userId);

    @Query(
            "SELECT ur.follower FROM UserRelationship ur "+
            "WHERE ur.following.userId = :userId"
    )
    Page<UserProfile> findAllFollowersOfUser(@Param("id") UUID id, Pageable pageable);

    @Query(
            "SELECT ur.following FROM UserRelationship ur "+
            "WHERE ur.follower.userId = :userId"
    )
    Page<UserProfile> findAllFollowingOfUser(@Param("id") UUID id, Pageable pageable);

    @Query(
            "DELETE FROM UserRelationship ur "+
            "WHERE ur.follower.userId = :userId "+
            "OR ur.following.userId = :userId"
    )
    void deleteAllByUserId(@Param("id") UUID id);

    void deleteByFollowerUserIdAndFollowingUserId(UUID followerId, UUID followingId);

    boolean existsByFollowerUserIdAndFollowingUserId(UUID followerId, UUID followingId);
}
