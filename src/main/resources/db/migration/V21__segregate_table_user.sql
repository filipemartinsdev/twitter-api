ALTER TABLE users RENAME TO user_credentials;

CREATE TABLE user_profile (
    user_id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user_credentials(id) ON DELETE CASCADE
);

CREATE INDEX ON user_profile (username);
CREATE INDEX ON user_profile (email);