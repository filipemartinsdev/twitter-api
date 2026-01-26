CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    followers_count BIGINT NOT NULL DEFAULT (0),
    following_count BIGINT NOT NULL DEFAULT (0),
    tweets_count BIGINT NOT NULL DEFAULT (0)
);