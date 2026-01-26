CREATE TABLE tweet (
    id UUID PRIMARY KEY,
    parent_id UUID REFERENCES tweet(id),
    user_id UUID REFERENCES users(id),
    content TEXT NOT NULL,
    likes_count BIGINT NOT NULL,
    comments_count BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);