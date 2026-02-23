package com.api.twitter.user.docs;

public class ResponseExamples {
    public static final String SUCCESS =
            """
            {
              "status": "success"
            }
            """;

    public static final String PAGED_USER_RESPONSE =
            """
            {
                "status": "success",
                "data": {
                    "size": 1,
                    "page": 0,
                    "totalPages": 5,
                    "totalElements": 5,
                    "isLast": false,
                    "content": [
                        {
                            "id": "123e4567-e89b...",
                            "username": "filipe",
                            "email": "filipe@gmail.com",
                            "tweetsCount": 100,
                            "followersCount": 200,
                            "followingCount": 150
                        }
                    ]
                }
            }
            """;

    public static final String USER_RESPONSE =
            """
            {
                "status": "success",
                "data": {
                    "id": "123e4567-e89b...",
                    "username": "filipe",
                    "email": "filipe@gmail.com",
                    "tweetsCount": 100,
                    "followersCount": 200,
                    "followingCount": 150
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

    public static final String USER_PROFILE_UPDATED_RESPONSE =
            """
            {
                "status": "success",
                "data": {
                    "userId": "123e4567-e89b...",
                    "username": "filipe",
                    "email": "filipe@gmail.com",
                    "description": "Just a regular Twitter user"
                }
            }
            """;

    public static final String INVALID_USER_UPDATE_REQUEST =
            """
            {
                "status": "fail",
                "data": {
                    "email": "must be a well-formed email address"
                }
            }
            """;
}
