ALTER TABLE user_dladle ALTER COLUMN password TYPE VARCHAR(500);
CREATE UNIQUE INDEX user_dladle_email_uindex ON user_dladle (emailid);

ALTER TABLE vendor
  DROP first_name;
ALTER TABLE vendor
  DROP last_name;
ALTER TABLE vendor
  DROP identity_number;
ALTER TABLE vendor
  DROP location;
ALTER TABLE vendor
  ADD COLUMN business_name VARCHAR(50);
ALTER TABLE vendor
  ADD COLUMN service_type_id INT;

ALTER TABLE vendor
  ADD FOREIGN KEY (service_type_id) REFERENCES service_type (id);

ALTER TABLE user_dladle add COLUMN verification_code VARCHAR(100);

ALTER TABLE user_dladle DROP COLUMN otp;
ALTER TABLE user_dladle ADD COLUMN otp INT;
ALTER TABLE user_dladle ADD COLUMN first_name VARCHAR(20);
ALTER TABLE user_dladle ADD COLUMN last_name VARCHAR(20);
ALTER TABLE user_dladle ADD COLUMN id_number VARCHAR(45);

ALTER TABLE landlord DROP COLUMN first_name;
ALTER TABLE landlord DROP COLUMN last_name;
ALTER TABLE landlord DROP COLUMN identity_number;
ALTER TABLE tenant DROP COLUMN first_name;
ALTER TABLE tenant DROP COLUMN last_name;
ALTER TABLE tenant DROP COLUMN identity_number;


ALTER TABLE property
  ADD COLUMN place_name VARCHAR(45);
ALTER TABLE property
  ADD COLUMN complex_name VARCHAR(45);
ALTER TABLE property
  ADD COLUMN unit_number VARCHAR(20);
ALTER TABLE property
  ADD COLUMN place_type_id INT;
ALTER TABLE property
  ADD COLUMN bedroom_type_id INT;
ALTER TABLE property
  ADD COLUMN image_url VARCHAR(100);
ALTER TABLE property
  ADD FOREIGN KEY (place_type_id) REFERENCES place_type (id);
ALTER TABLE property
  ADD FOREIGN KEY (bedroom_type_id) REFERENCES bedroom_type (id);
