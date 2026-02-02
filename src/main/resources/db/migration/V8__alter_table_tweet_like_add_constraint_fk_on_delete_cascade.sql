ALTER TABLE tweet_like
ADD CONSTRAINT fk_tweet_like_tweet
FOREIGN KEY (tweet_id)
REFERENCES tweet(id)
ON DELETE CASCADE;