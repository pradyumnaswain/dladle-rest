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

ALTER TABLE rating
  DROP COLUMN user_id;
ALTER TABLE rating
  ADD COLUMN rated_user INT;
ALTER TABLE rating
  ADD COLUMN rating_user INT;
ALTER TABLE rating
  ADD COLUMN rating_comment VARCHAR(500);
ALTER TABLE rating
  ADD FOREIGN KEY (rated_user) REFERENCES user_dladle (id);
ALTER TABLE rating
  ADD FOREIGN KEY (rating_user) REFERENCES user_dladle (id);
ALTER TABLE rating
  ADD CONSTRAINT rating_rated_user UNIQUE (rating_user, rated_user);

DROP TABLE rating_comments;

ALTER TABLE house
  DROP COLUMN house_number;

ALTER TABLE user_dladle
  ADD COLUMN device_id VARCHAR(5000);
ALTER TABLE user_device
  DROP COLUMN user_id;


ALTER TABLE house
  DROP COLUMN notifications_count;
ALTER TABLE house
  ADD COLUMN notifications_count_tenant INT DEFAULT 0;
ALTER TABLE house
  ADD COLUMN notifications_count_landlord INT DEFAULT 0;
ALTER TABLE house
  ADD COLUMN notifications_count_vendor INT DEFAULT 0;
ALTER TABLE house
  ADD COLUMN active_job BOOLEAN DEFAULT FALSE;
ALTER TABLE house
  ADD COLUMN contacts_count INT DEFAULT 0;
ALTER TABLE house
  ADD COLUMN is_home BOOLEAN DEFAULT FALSE;
ALTER TABLE house
  ADD COLUMN tenants_count INT DEFAULT 0;

ALTER TABLE property_contact
  DROP COLUMN property_id;
ALTER TABLE property_contact
  ADD COLUMN house_id INT;
ALTER TABLE property_contact
  ADD FOREIGN KEY (house_id) REFERENCES house (id);

ALTER TABLE tenant
  DROP COLUMN landlord_id;
ALTER TABLE notification
  ADD COLUMN house_id INT;
ALTER TABLE notification
  ADD FOREIGN KEY (house_id) REFERENCES house (id);


ALTER TABLE user_dladle
  ADD COLUMN profile_picture VARCHAR(1000);

ALTER TABLE lease
  DROP COLUMN tenant_id;
ALTER TABLE lease
  DROP COLUMN lease_date;
ALTER TABLE lease
  DROP COLUMN renewal_date;
ALTER TABLE lease
  ADD COLUMN lease_start_date TIMESTAMP;
ALTER TABLE lease
  ADD COLUMN lease_terminate_date TIMESTAMP;
ALTER TABLE lease
  ADD COLUMN lease_end_date TIMESTAMP;
ALTER TABLE lease
  ADD COLUMN lease_renewal_date TIMESTAMP;
ALTER TABLE lease
  DROP COLUMN landlord_id;
ALTER TABLE lease
  ADD COLUMN house_id INT;
ALTER TABLE lease
  ADD COLUMN lease_status BOOLEAN DEFAULT FALSE;
ALTER TABLE lease
  ADD FOREIGN KEY (house_id) REFERENCES house (id);
ALTER TABLE lease_tenant
  ADD UNIQUE (lease_id, tenant_id);

ALTER TABLE notification
  ADD COLUMN notification_type_id INT;
ALTER TABLE notification
  ADD FOREIGN KEY (notification_type_id) REFERENCES notification_type (id);

ALTER TABLE house
  DROP COLUMN notifications_count_tenant;
ALTER TABLE house
  DROP COLUMN notifications_count_landlord;
ALTER TABLE house
  DROP COLUMN notifications_count_vendor;

DROP TABLE notification_count;

CREATE TABLE notification_count
(
  id      SERIAL PRIMARY KEY NOT NULL,
  user_id INT,
  count   INT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  UNIQUE (user_id)
);

ALTER TABLE lease
  ADD COLUMN lease_renewal_notification_date TIMESTAMP;
ALTER TABLE lease_tenant
  ADD COLUMN lease_status BOOLEAN DEFAULT FALSE;
ALTER TABLE lease_tenant
  ADD COLUMN leave_date TIMESTAMP;

ALTER TABLE service
  ADD COLUMN service_request_time TIMESTAMP;
ALTER TABLE service
  ADD COLUMN service_need_time TIMESTAMP;
ALTER TABLE service
  ADD COLUMN service_approved_time TIMESTAMP;
ALTER TABLE service
  ADD COLUMN service_start_time TIMESTAMP;
ALTER TABLE service
  ADD COLUMN service_end_time TIMESTAMP;
ALTER TABLE service
  ADD COLUMN service_status_id BIGINT;
ALTER TABLE service
  ADD COLUMN service_requester_user_id BIGINT;
ALTER TABLE service
  ADD COLUMN service_paid_user_id BIGINT;
ALTER TABLE service
  ADD COLUMN service_expected_fee_range_start FLOAT;
ALTER TABLE service
  ADD COLUMN service_expected_fee_range_end FLOAT;
ALTER TABLE service
  ADD COLUMN service_fee FLOAT;
ALTER TABLE service
  ADD COLUMN service_fee_paid BOOLEAN DEFAULT FALSE;
ALTER TABLE service
  ADD COLUMN emergency BOOLEAN DEFAULT FALSE;
ALTER TABLE service
  ADD COLUMN service_description VARCHAR(500);
ALTER TABLE service
  ADD COLUMN house_id BIGINT;
ALTER TABLE service
  ADD FOREIGN KEY (house_id) REFERENCES house (id);
ALTER TABLE service
  ADD FOREIGN KEY (service_status_id) REFERENCES service_status (id);
ALTER TABLE service
  ADD FOREIGN KEY (service_requester_user_id) REFERENCES user_dladle (id);
ALTER TABLE service
  ADD FOREIGN KEY (service_paid_user_id) REFERENCES user_dladle (id);
ALTER TABLE service
  DROP COLUMN service_paid_user_id;

ALTER TABLE payment_card
  DROP COLUMN expiry_date;
ALTER TABLE payment_card
  ADD COLUMN vaultId VARCHAR(500);

ALTER TABLE vendor
  ADD COLUMN account_set BOOLEAN DEFAULT FALSE;
ALTER TABLE vendor
  ADD COLUMN account_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE user_dladle
  ADD COLUMN payment_account_set BOOLEAN DEFAULT FALSE;
DELETE FROM contact_type
WHERE id = 4;
DROP TABLE tenant_property_images_and_notes;
CREATE TABLE tenant_property_documents
(
  id            BIGSERIAL PRIMARY KEY NOT NULL,
  tenant_id     BIGINT,
  url           VARCHAR(500),
  document_type BIGINT,
  valid         BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  FOREIGN KEY (document_type) REFERENCES document_type (id)
);

ALTER TABLE notification_count
  ADD COLUMN house_id BIGINT;
ALTER TABLE notification_count
  ADD FOREIGN KEY (house_id) REFERENCES house (id);
ALTER TABLE notification_count
  DROP CONSTRAINT notification_count_user_id_key;
ALTER TABLE notification_count
  ADD UNIQUE (user_id, house_id);
ALTER TABLE user_dladle
  ADD COLUMN rgistered_date TIMESTAMP;
ALTER TABLE user_dladle
  ADD COLUMN last_logged_in_date TIMESTAMP;
ALTER TABLE property
  ADD COLUMN property_add_date DATE;
ALTER TABLE rating
  ADD COLUMN rated_date DATE;
ALTER TABLE tenant_property_documents
  ADD COLUMN add_date DATE;
ALTER TABLE lease
  ALTER COLUMN lease_start_date TYPE DATE;
ALTER TABLE lease
  ALTER COLUMN lease_end_date TYPE DATE;
ALTER TABLE lease
  ALTER COLUMN lease_terminate_date TYPE DATE;
ALTER TABLE lease
  ALTER COLUMN lease_renewal_date TYPE DATE;
ALTER TABLE lease
  ALTER COLUMN lease_renewal_notification_date TYPE DATE;
ALTER TABLE lease_tenant
  ALTER COLUMN joined_date TYPE DATE;
ALTER TABLE lease_tenant
  ALTER COLUMN leave_date TYPE DATE;
ALTER TABLE notification
  DROP CONSTRAINT notification_house_id_fkey;
ALTER TABLE notification_count
  DROP CONSTRAINT notification_house_id_fkey;
ALTER TABLE vendor_work_timeline
  DROP COLUMN current_location;
ALTER TABLE vendor_work_timeline
  ADD COLUMN current_location_latitude VARCHAR(100);
ALTER TABLE vendor_work_timeline
  ADD COLUMN current_location_longitude VARCHAR(100);
ALTER TABLE property
  ADD COLUMN address_latitude VARCHAR(100);
ALTER TABLE property
  ADD COLUMN address_longitude VARCHAR(100);

UPDATE user_dladle
SET cell_number = ''
WHERE cell_number ISNULL;
ALTER TABLE user_dladle
  ALTER COLUMN cell_number SET DEFAULT '';
DROP TABLE payment_card;
CREATE TABLE payment_card
(
  id               BIGSERIAL PRIMARY KEY,
  user_id          BIGINT,
  card_update_time TIMESTAMP,
  name_on_card     VARCHAR(100),
  card_number      VARCHAR(16),
  card_type        VARCHAR(100),
  expiry_date      VARCHAR(5),
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  UNIQUE (user_id, card_number)
);
DROP TABLE transaction;
CREATE TABLE transaction
(
  id                 BIGSERIAL PRIMARY KEY,
  reference_no       VARCHAR(100),
  user_id            BIGINT,
  amount             DECIMAL,
  transcation_type   BIGINT DEFAULT 2,
  operation_type     BIGINT,
  transaction_status BIGINT DEFAULT 4,
  transaction_time   TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (transcation_type) REFERENCES transaction_type (id),
  FOREIGN KEY (operation_type) REFERENCES operation_type (id),
  FOREIGN KEY (transaction_status) REFERENCES transaction_status (id)
);
ALTER TABLE user_dladle
  ADD COLUMN status BOOLEAN DEFAULT TRUE;
ALTER TABLE property
  ADD COLUMN status BOOLEAN DEFAULT TRUE;
ALTER TABLE user_dladle
  ADD COLUMN account_locked BOOLEAN DEFAULT FALSE;


CREATE TABLE vendor_rejection_reason
(
  id               BIGINT PRIMARY KEY,
  rejection_reason VARCHAR(100)
);
CREATE TABLE service_over_price_reason
(
  id                       BIGINT PRIMARY KEY,
  over_price_reason_reason VARCHAR(100)
);


ALTER TABLE service
  ADD COLUMN vendror_rejection_reason_id BIGINT;
ALTER TABLE service
  ADD FOREIGN KEY (vendror_rejection_reason_id) REFERENCES vendor_rejection_reason (id);
ALTER TABLE user_dladle
  ADD COLUMN deleted_date TIMESTAMP;

ALTER TABLE transaction
  ADD COLUMN card_id BIGINT;
ALTER TABLE transaction
  ADD FOREIGN KEY (card_id) REFERENCES payment_card (id);

ALTER TABLE property
  ADD COLUMN deleted_date TIMESTAMP;
