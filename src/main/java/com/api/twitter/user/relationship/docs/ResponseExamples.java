package com.api.twitter.user.relationship.docs;

public class ResponseExamples {
    public static final String FOLLOW_USER_SUCCESS = """
        {
          "status": "success"
        }
        """;
    public static final String UNFOLLOW_USER_SUCCESS = """
        {
          "status": "success"
        }
        """;
    public static final String ALREADY_FOLLOWING = """
        {
          "status": "fail",
          "message": "Its already following this user"
        }
        """;
    public static final String NOT_FOLLOWING_YET = """
        {
          "status": "fail",
          "message": "Its not following this user yet"
        }
        """;
    public static final String USER_NOT_FOUND = """
        {
          "status": "fail",
          "message": "User not found"
        }
        """;
}
