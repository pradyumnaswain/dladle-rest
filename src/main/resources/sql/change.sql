ALTER TABLE user_dladle ALTER COLUMN password TYPE VARCHAR(500);
CREATE UNIQUE INDEX user_dladle_email_uindex ON user_dladle (emailid);