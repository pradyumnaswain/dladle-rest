ALTER TABLE landlord
  DROP COLUMN cell_number;
ALTER TABLE vendor
  DROP COLUMN cell_number;
ALTER TABLE user_dladle
  ADD COLUMN cell_number INT;