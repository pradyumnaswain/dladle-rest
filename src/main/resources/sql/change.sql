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
  ADD COLUMN isEstate boolean;

ALTER TABLE property
  ADD COLUMN estate_name VARCHAR(100);
