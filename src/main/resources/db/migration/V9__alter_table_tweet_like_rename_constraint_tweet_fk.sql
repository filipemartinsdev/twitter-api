ALTER TABLE tweet_like DROP CONSTRAINT fk_tweet_like_tweet;

ALTER TABLE tweet_like
ADD CONSTRAINT tweet_like_tweet_fkey
FOREIGN KEY (tweet_id)
REFERENCES tweet(id)
ON DELETE CASCADE;