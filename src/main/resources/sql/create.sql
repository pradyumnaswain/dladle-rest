-- 1. Install PostgreSQL.
-- 2. In command Line type psql postgres postgres then enter
-- 3. Then give password postgres
/* Create User*/
-- DROP USER sysadmin;
CREATE USER sysadmin WITH PASSWORD 'masterkey';
/* Create Database*/
-- DROP DATABASE dladle;
CREATE DATABASE dladle WITH OWNER = sysadmin;
/* Grant privileges*/
GRANT ALL PRIVILEGES ON DATABASE dladle TO sysadmin;

-- DROP OWNED BY sysadmin;

/*User Type*/
CREATE TABLE user_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(128)
);

/*service_type*/
CREATE TABLE service_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(128)
);

/*amenities_type*/
CREATE TABLE amenities_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(128)
);
/*place_type*/
CREATE TABLE place_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20)
);
/*bedroom_type*/
CREATE TABLE bedroom_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20)
);

/*Home View Type*/
CREATE TABLE home_view_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20)
);

/*Years of Experience*/
CREATE TABLE years_exp
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20)
);

/*Contact Type*/
CREATE TABLE contact_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20)
);
/*Notification Type*/
CREATE TABLE notification_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(100)
);
/*Service Status*/
CREATE TABLE service_status
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(100)
);
/*Transaction Status*/
CREATE TABLE transaction_status
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(100)
);
/*Transaction Type*/
CREATE TABLE transaction_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(100)
);
/*Operation Type*/
CREATE TABLE operation_type
(
  id   BIGINT PRIMARY KEY,
  name VARCHAR(100)
);

/* User*/
CREATE TABLE user_dladle
(
  id                  BIGSERIAL PRIMARY KEY,
  emailId             VARCHAR(100) NOT NULL,
  password            VARCHAR(500) NOT NULL,
  first_name          VARCHAR(20),
  last_name           VARCHAR(20),
  profile_picture     VARCHAR(1000),
  id_number           VARCHAR(45),
  cell_number         VARCHAR(10) DEFAULT '',
  user_type_id        BIGINT,
  rgistered_date      TIMESTAMP,
  last_logged_in_date TIMESTAMP,
  verified            BOOLEAN,
  verification_code   VARCHAR(100),
  otp                 BIGINT,
  device_id           VARCHAR(1000),
  deleted_date        TIMESTAMP,
  payment_account_set BOOLEAN     DEFAULT FALSE,
  status              BOOLEAN     DEFAULT TRUE,
  account_locked      BOOLEAN     DEFAULT FALSE,
  FOREIGN KEY (user_type_id) REFERENCES user_type (id)
);
CREATE UNIQUE INDEX user_dladle_email_uindex
  ON user_dladle (emailid);
/*landlord*/
CREATE TABLE landlord
(
  id                BIGSERIAL PRIMARY KEY,
  user_id           BIGINT,
  home_view_type_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (home_view_type_id) REFERENCES home_view_type (id)
);

/*property*/
CREATE TABLE property
(
  id                BIGSERIAL PRIMARY KEY,
  landlord_id       BIGINT,
  place_type_id     BIGINT,
  address           VARCHAR(2000),
  address_latitude  VARCHAR(100),
  address_longitude VARCHAR(100),
  isEstate          BOOLEAN,
  estate_name       VARCHAR(500),
  complex_name      VARCHAR(100),
  unit_number       VARCHAR(100),
  image_url         VARCHAR(100),
  property_add_date DATE,
  deleted_date      TIMESTAMP,
  status            BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (place_type_id) REFERENCES place_type (id),
  FOREIGN KEY (landlord_id) REFERENCES landlord (id)
);

/*property_contact*/
CREATE TABLE property_contact
(
  id              BIGSERIAL PRIMARY KEY,
  house_id        BIGINT,
  contact_type_id BIGINT,
  name            VARCHAR(50),
  address         VARCHAR(200),
  contact_number  VARCHAR(12),
  FOREIGN KEY (house_id) REFERENCES property (id),
  FOREIGN KEY (contact_type_id) REFERENCES contact_type (id)
);

/*house*/
CREATE TABLE house
(
  id             BIGSERIAL PRIMARY KEY,
  property_id    BIGINT,
  active_job     BOOLEAN DEFAULT FALSE,
  contacts_count BIGINT  DEFAULT 0,
  is_home        BOOLEAN DEFAULT FALSE,
  tenants_count  BIGINT  DEFAULT 0,
  FOREIGN KEY (property_id) REFERENCES property (id)
);

/*amenities*/
CREATE TABLE amenities
(
  id                BIGSERIAL PRIMARY KEY,
  amenities_type_id BIGINT,
  count             BIGINT,
  house_id          BIGINT,
  FOREIGN KEY (amenities_type_id) REFERENCES amenities_type (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
);

/*rating*/
CREATE TABLE rating
(
  id             BIGSERIAL PRIMARY KEY,
  rated_user     BIGINT,
  rating_user    BIGINT,
  value          DECIMAL,
  rating_comment VARCHAR(500),
  rated_date     DATE,
  FOREIGN KEY (rated_user) REFERENCES user_dladle (id),
  FOREIGN KEY (rating_user) REFERENCES user_dladle (id),
  UNIQUE (rating_user, rated_user)
);

/*tenant*/
CREATE TABLE tenant
(
  id       BIGSERIAL PRIMARY KEY,
  user_id  BIGINT,
  house_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
);
CREATE TABLE document_type
(
  id   BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(20)
);

/*tenant*/
CREATE TABLE tenant_property_documents
(
  id            BIGSERIAL PRIMARY KEY,
  tenant_id     BIGINT,
  url           VARCHAR(500),
  document_type BIGINT,
  valid         BOOLEAN DEFAULT TRUE,
  add_date      DATE,
  FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  FOREIGN KEY (document_type) REFERENCES document_type (id)
);
/*tenant*/
CREATE TABLE vendor
(
  id               BIGSERIAL PRIMARY KEY,
  user_id          BIGINT,
  business_address VARCHAR(50),
  service_type_id  BIGINT,
  transport        BOOLEAN DEFAULT FALSE,
  tools            BOOLEAN DEFAULT FALSE,
  experience_id    BIGINT,
  account_set      BOOLEAN DEFAULT FALSE,
  account_verified BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (experience_id) REFERENCES years_exp (id),
  FOREIGN KEY (user_id) REFERENCES user_dladle (id)
);

/*leaseTenant*/
CREATE TABLE lease
(
  id                              BIGSERIAL PRIMARY KEY,
  house_id                        BIGINT,
  lease_start_date                DATE,
  lease_terminate_date            DATE,
  lease_end_date                  DATE,
  lease_renewal_date              DATE,
  lease_renewal_notification_date DATE,
  remark                          VARCHAR(100),
  lease_status                    BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (house_id) REFERENCES house (id)
);
/*service*/
CREATE TABLE service
(
  id                               BIGSERIAL PRIMARY KEY,
  service_type_id                  BIGINT,
  emergency                        BOOLEAN DEFAULT FALSE,
  service_request_time             TIMESTAMP,
  service_need_time                TIMESTAMP,
  service_description              VARCHAR(500),
  service_approved_time            TIMESTAMP,
  service_start_time               TIMESTAMP,
  service_end_time                 TIMESTAMP,
  service_requester_user_id        BIGINT,
  service_expected_fee_range_start FLOAT,
  service_expected_fee_range_end   FLOAT,
  service_fee                      FLOAT,
  service_fee_paid                 BOOLEAN DEFAULT FALSE,
  service_status_id                BIGINT,
  vendor_id                        BIGINT,
  house_id                         BIGINT,
  FOREIGN KEY (house_id) REFERENCES house (id),
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (vendor_id) REFERENCES vendor (id),
  FOREIGN KEY (service_status_id) REFERENCES service_status (id),
  FOREIGN KEY (service_requester_user_id) REFERENCES user_dladle (id)
);
CREATE TABLE service_documents
(
  id            BIGSERIAL PRIMARY KEY,
  service_id    BIGINT,
  url           VARCHAR(500),
  document_type BIGINT,
  FOREIGN KEY (service_id) REFERENCES service (id),
  FOREIGN KEY (document_type) REFERENCES document_type (id)
);
CREATE TABLE vendor_work_timeline
(
  id                         BIGSERIAL PRIMARY KEY,
  vendor_id                  BIGINT,
  current_work_status        BOOLEAN DEFAULT FALSE,
  on_work_from               TIMESTAMP,
  on_work_to                 TIMESTAMP,
  current_location_latitude  VARCHAR(100),
  current_location_longitude VARCHAR(100),
  FOREIGN KEY (vendor_id) REFERENCES vendor (id)
);
/*User Device*/
CREATE TABLE user_device
(
  id        BIGSERIAL PRIMARY KEY,
  device_id VARCHAR(1000),
  CONSTRAINT unique_device UNIQUE (device_id)
);

/*tenant_contact*/
CREATE TABLE tenant_contact
(
  id              BIGSERIAL PRIMARY KEY,
  tenant_id       BIGINT,
  house_id        BIGINT,
  contact_type_id BIGINT,
  name            VARCHAR(50),
  address         VARCHAR(200),
  contact_number  VARCHAR(12),
  FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  FOREIGN KEY (house_id) REFERENCES house (id),
  FOREIGN KEY (contact_type_id) REFERENCES contact_type (id)
);
/*Notification*/
CREATE TABLE notification
(
  id                           BIGSERIAL PRIMARY KEY,
  notification_type_id         BIGINT,
  notification_from            BIGINT,
  notification_to              BIGINT,
  notification_title           VARCHAR(100),
  notification_body            VARCHAR(1000),
  notification_data            VARCHAR(1000),
  notification_image_url       VARCHAR(200),
  notification_time            TIMESTAMP,
  notification_read_status     BOOLEAN,
  notification_actioned_status BOOLEAN,
  house_id                     BIGINT,
  FOREIGN KEY (notification_from) REFERENCES user_dladle (id),
  FOREIGN KEY (notification_to) REFERENCES user_dladle (id),
  FOREIGN KEY (notification_type_id) REFERENCES notification_type (id)
);
CREATE TABLE notification_count
(
  id       BIGSERIAL PRIMARY KEY,
  user_id  BIGINT,
  count    BIGINT,
  house_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  UNIQUE (user_id, house_id)
);

CREATE TABLE lease_tenant
(
  id           BIGSERIAL PRIMARY KEY,
  lease_id     BIGINT,
  tenant_id    BIGINT,
  joined_date  DATE,
  leave_date   DATE,
  lease_status BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  FOREIGN KEY (lease_id) REFERENCES lease (id),
  UNIQUE (lease_id, tenant_id)
);

CREATE TABLE payment_card
(
  id               BIGSERIAL PRIMARY KEY,
  user_id          BIGINT,
  card_update_time TIMESTAMP,
  name_on_card     VARCHAR(100),
  card_number      VARCHAR(16),
  card_type        VARCHAR(100),
  expiry_date      VARCHAR(6),
  card_id          BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (card_id) REFERENCES payment_card (id),
  UNIQUE (user_id, card_number)
);
CREATE TABLE service_estimations
(
  id              BIGSERIAL PRIMARY KEY,
  service_id      BIGINT,
  vendor_id       BIGINT,
  experience      VARCHAR(10),
  proximity       DOUBLE PRECISION,
  rating          DOUBLE PRECISION,
  fee_start_range DOUBLE PRECISION,
  fee_end_range   DOUBLE PRECISION,
  notification_sent_time TIMESTAMP,
  estimation_response_time TIMESTAMP,
  FOREIGN KEY (service_id) REFERENCES service (id),
  FOREIGN KEY (vendor_id) REFERENCES vendor (id)
);
CREATE TABLE transaction
(
  id                 BIGSERIAL PRIMARY KEY,
  reference_no       VARCHAR(100),
  user_id            BIGINT,
  card_id            BIGINT,
  amount             DECIMAL,
  transaction_type   BIGINT DEFAULT 2,
  operation_type     BIGINT,
  transaction_status BIGINT DEFAULT 4,
  transaction_time   TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (card_id) REFERENCES payment_card (id),
  FOREIGN KEY (transaction_type) REFERENCES transaction_type (id),
  FOREIGN KEY (operation_type) REFERENCES operation_type (id),
  FOREIGN KEY (transaction_status) REFERENCES transaction_status (id)
);
CREATE TABLE vendor_rejection_reason
(
  id               BIGINT PRIMARY KEY,
  rejection_reason VARCHAR(100)
);
CREATE TABLE service_over_price_reason
(
  id                BIGINT PRIMARY KEY,
  over_price_reason VARCHAR(100)
);

