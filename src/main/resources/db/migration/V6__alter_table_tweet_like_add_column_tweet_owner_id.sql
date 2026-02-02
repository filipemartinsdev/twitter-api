ALTER TABLE tweet_like
ADD COLUMN tweet_owner_id UUID REFERENCES users(id) NOT NULL;