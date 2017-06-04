ALTER TABLE landlord
  DROP COLUMN cell_number;
ALTER TABLE vendor
  DROP COLUMN cell_number;
ALTER TABLE user_dladle
  ADD COLUMN cell_number INT;
ALTER TABLE property
  DROP COLUMN home_view_type_id;

ALTER TABLE landlord
  ADD COLUMN home_view_type_id INT;
ALTER TABLE landlord
  ADD FOREIGN KEY (home_view_type_id) REFERENCES home_view_type (id);
ALTER TABLE vendor
  ADD COLUMN experience_id INT;
ALTER TABLE vendor
  ADD FOREIGN KEY (experience_id) REFERENCES years_exp (id);

ALTER TABLE property
  DROP COLUMN place_name;

ALTER TABLE property
  DROP COLUMN location;

ALTER TABLE property
  DROP COLUMN bedroom_type_id;

ALTER TABLE property
  ADD COLUMN isEstate BOOLEAN;

ALTER TABLE property
  ADD COLUMN estate_name VARCHAR(100);
ALTER TABLE user_dladle
  ALTER COLUMN cell_number TYPE VARCHAR(45) USING cell_number :: VARCHAR(45);
ALTER TABLE vendor
  DROP COLUMN business_name;

ALTER TABLE rating DROP COLUMN user_id;
ALTER TABLE rating ADD COLUMN rated_user INT;
ALTER TABLE rating ADD COLUMN rating_user INT;
ALTER TABLE rating ADD COLUMN rating_comment VARCHAR(500);
ALTER TABLE rating ADD FOREIGN KEY (rated_user) REFERENCES user_dladle(id);
ALTER TABLE rating ADD FOREIGN KEY (rating_user) REFERENCES user_dladle(id);
ALTER TABLE rating ADD CONSTRAINT rating_rated_user UNIQUE (rating_user,rated_user) ;

DROP TABLE rating_comments;

ALTER TABLE house DROP COLUMN house_number;

ALTER TABLE user_dladle ADD COLUMN device_id VARCHAR(5000);
ALTER TABLE user_device DROP COLUMN user_id;
