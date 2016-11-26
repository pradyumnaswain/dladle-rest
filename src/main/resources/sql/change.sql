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