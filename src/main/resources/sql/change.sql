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