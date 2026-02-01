package com.api.twitter.user.relationship.infrastructure.persistence;

import com.api.twitter.user.domain.User;
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
            "SELECT ur.follower FROM UserRelationship ur "+
            "WHERE ur.following.id = :id"
    )
    Page<User> findAllFollowersOfUser(@Param("id") UUID id, Pageable pageable);

    @Query(
            "SELECT ur.following FROM UserRelationship ur "+
            "WHERE ur.follower.id = :id"
    )
    Page<User> findAllFollowingOfUser(@Param("id") UUID id, Pageable pageable);

    @Query(
            "DELETE FROM UserRelationship ur "+
            "WHERE ur.follower.id = :id "+
            "OR ur.following.id = :id"
    )
    void deleteAllByUserId(@Param("id") UUID id);

    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);

    boolean existsByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
}
