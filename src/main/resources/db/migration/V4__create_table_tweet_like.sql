CREATE TABLE tweet_like (
    tweet_id UUID REFERENCES tweet(id) NOT NULL,
    user_id UUID REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (tweet_id, user_id)
);