ALTER TABLE user_relationship
DROP CONSTRAINT IF EXISTS user_relationship_follower_id_fkey;

ALTER TABLE user_relationship
DROP CONSTRAINT IF EXISTS user_relationship_following_id_fkey;

ALTER TABLE user_relationship
ADD CONSTRAINT user_relationship_follower_id_fkey
FOREIGN KEY (follower_id) REFERENCES users(id)
ON DELETE CASCADE;

ALTER TABLE user_relationship
ADD CONSTRAINT user_relationship_following_id_fkey
FOREIGN KEY (following_id) REFERENCES users(id)
ON DELETE CASCADE;
