ALTER TABLE tweet
DROP CONSTRAINT IF EXISTS"tweet_parent_id_fkey";

ALTER TABLE tweet
ADD CONSTRAINT "tweet_parent_id_fkey"
FOREIGN KEY (parent_id) REFERENCES tweet(id)
ON DELETE CASCADE;