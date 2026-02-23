package com.api.twitter.tweet.docs;

public class ResponseExamples {
    public static final String SUCCESS =
            """
            {
              "status": "success"
            }
            """;
    
    public static final String TWEET_RESPONSE =
            """
            {
                "status": "success",
                "data": {
                    "id": "10a42e51-6014...",
                    "parentId": null,
                    "content": "Just a usual tweet",
                    "createdAt": "2026-02-22T22:44:35.518079638",
                    "viewsCount": 0,
                    "likesCount": 0,
                    "commentsCount": 1,
                    "user": {
                        "id": "a699cd67-3c90-4318-83d8-e3290d2b70ee",
                        "username": "test"
                    }
                }
            }
            """;

    public static final String COMMENT_RESPONSE =
            """
            {
                "status": "success",
                "data": {
                    "id": "10a42e51-6014...",
                    "parentId": "a699cd67-3c90...",
                    "content": "Just a usual comment",
                    "createdAt": "2026-02-22T22:44:35.518079638",
                    "viewsCount": 0,
                    "likesCount": 0,
                    "commentsCount": 0,
                    "user": {
                        "id": "a699cd67-3c90-4318-83d8-e3290d2b70ee",
                        "username": "test"
                    }
                }
            }
            """;

    public static final String TWEET_CANT_BE_BLANK =
            """
            {
                "status": "fail",
                "data": {
                    "content": "Tweet content can't be blank"
                }
            }
            """;

    public static final String TWEET_NOT_FOUND =
            """
            {
                "status": "fail",
                "message": "Tweet not found"
            }
            """;

    public static final String PAGED_TWEETS =
            """
            {
                "status": "success",
                "data": {
                    "size": 20,
                    "page": 0,
                    "totalPages": 1,
                    "totalElements": 2,
                    "isLast": true,
                    "content": [
                        {
                            "id": "10a42e51-6014...",
                            "parentId": null,
                            "content": "Just a usual tweet",
                            "createdAt": "2026-02-22T22:44:35.51808",
                            "viewsCount": 0,
                            "likesCount": 0,
                            "commentsCount": 0,
                            "user": {
                                "id": "a699cd67-3c90...",
                                "username": "test"
                            }
                        }
                    ]
                }
            }
            """;

    public static final String PAGED_COMMENTS =
            """
            {
                "status": "success",
                "data": {
                    "size": 20,
                    "page": 0,
                    "totalPages": 1,
                    "totalElements": 2,
                    "isLast": true,
                    "content": [
                        {
                            "id": "10a42e51-6014...",
                            "parentId": "a699cd67-3c90...",
                            "content": "Just a usual comment",
                            "createdAt": "2026-02-22T22:44:35.51808",
                            "viewsCount": 0,
                            "likesCount": 0,
                            "commentsCount": 0,
                            "user": {
                                "id": "a699cd67-3c90...",
                                "username": "test"
                            }
                        }
                    ]
                }
            }
            """;
    public static final String USER_NOT_FOUND =
            """
            {
                "status": "fail",
                "message": "User not found"
            }
            """;

    public static final String USER_HAS_ALREADY_LIKED =
            """
            {
                "status": "fail",
                "message": "User has already liked this tweet"
            }
            """;

    public static final String USER_HASNT_LIKED =
            """
            {
                "status": "fail",
                "message": "User hasn't liked this tweet yet"
            }
            """;

    public static final String USER_HAS_ALREADY_VIEWED =
            """
            {
                "status": "fail",
                "message": "User has already viewed this tweet"
            }
            """;

    public static final String CANT_VIEW_YOURSELF =
            """
            {
                "status": "fail",
                "message": "Can't view your self tweet"
            }
            """;

    public static final String USER_IS_NOT_OWNER =
            """
            {
              "status": "fail",
              "message": "Authenticated user is not owner of this tweet"
            }
            """;
}
