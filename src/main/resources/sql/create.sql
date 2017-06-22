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
  id   BIGINT PRIMARY KEY  NOT NULL,
  name VARCHAR(128)        NOT NULL
);

/*service_type*/
CREATE TABLE service_type
(
  id   BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
);

/*amenities_type*/
CREATE TABLE amenities_type
(
  id   BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
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

/* User*/
CREATE TABLE user_dladle
(
  id                BIGSERIAL PRIMARY KEY  NOT NULL,
  emailId           VARCHAR(100)           NOT NULL,
  password          VARCHAR(500)           NOT NULL,
  user_type_id      BIGINT,
  verified          BOOLEAN,
  verification_code VARCHAR(100),
  otp               BIGINT,
  first_name        VARCHAR(20),
  last_name         VARCHAR(20),
  id_number         VARCHAR(45),
  cell_number       VARCHAR(10),
  device_id         VARCHAR(1000),
  profile_picture   VARCHAR(1000),
  FOREIGN KEY (user_type_id) REFERENCES user_type (id)
);
CREATE UNIQUE INDEX user_dladle_email_uindex
  ON user_dladle (emailid);
/*landlord*/
CREATE TABLE landlord
(
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  user_id           BIGINT,
  home_view_type_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (home_view_type_id) REFERENCES home_view_type (id)
);

/*property*/
CREATE TABLE property
(
  id            BIGSERIAL PRIMARY KEY NOT NULL,
  landlord_id   BIGINT,
  place_type_id BIGINT,
  address       VARCHAR(100),
  isEstate      VARCHAR(100),
  estate_name   VARCHAR(100),
  complex_name  VARCHAR(45),
  unit_number   VARCHAR(20),
  image_url     VARCHAR(100),
  FOREIGN KEY (place_type_id) REFERENCES place_type (id),
  FOREIGN KEY (landlord_id) REFERENCES landlord (id)
);

/*property_contact*/
CREATE TABLE property_contact
(
  id              BIGSERIAL PRIMARY KEY NOT NULL,
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
  id             BIGSERIAL PRIMARY KEY NOT NULL,
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
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  amenities_type_id BIGINT,
  count             BIGINT,
  house_id          BIGINT,
  FOREIGN KEY (amenities_type_id) REFERENCES amenities_type (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
);

/*rating*/
CREATE TABLE rating
(
  id             BIGSERIAL PRIMARY KEY NOT NULL,
  rated_user     BIGINT,
  rating_user    BIGINT,
  value          DECIMAL,
  rating_comment VARCHAR(500),
  FOREIGN KEY (rated_user) REFERENCES user_dladle (id),
  FOREIGN KEY (rating_user) REFERENCES user_dladle (id),
  UNIQUE (rating_user, rated_user)
);

/*tenant*/
CREATE TABLE tenant
(
  id       BIGSERIAL PRIMARY KEY NOT NULL,
  user_id  BIGINT,
  house_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
);
/*tenant*/
CREATE TABLE vendor
(
  id               BIGSERIAL PRIMARY KEY NOT NULL,
  user_id          BIGINT,
  business_address VARCHAR(50),
  service_type_id  BIGINT,
  transport        BOOLEAN,
  tools            BOOLEAN,
  experience_id    BIGINT,
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (experience_id) REFERENCES years_exp (id),
  FOREIGN KEY (user_id) REFERENCES user_dladle (id)
);

/*leaseTenant*/
CREATE TABLE lease
(
  id                              BIGSERIAL PRIMARY KEY NOT NULL,
  house_id                        BIGINT,
  lease_start_date                TIMESTAMP,
  lease_terminate_date            TIMESTAMP,
  lease_end_date                  TIMESTAMP,
  lease_renewal_date              TIMESTAMP,
  lease_renewal_notification_date TIMESTAMP,
  remark                          VARCHAR(100),
  lease_status                    BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (house_id) REFERENCES house (id)
);
/*service*/
CREATE TABLE service
(
  id              BIGSERIAL PRIMARY KEY NOT NULL,
  service_type_id BIGINT,
  vendor_id       BIGINT,
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (vendor_id) REFERENCES vendor (id)
);
DROP TABLE user_device;
/*User Device*/
CREATE TABLE user_device
(
  id        BIGSERIAL PRIMARY KEY NOT NULL,
  device_id VARCHAR(1000),
  CONSTRAINT unique_device UNIQUE (device_id)
);

/*tenant_contact*/
CREATE TABLE tenant_contact
(
  id              BIGSERIAL PRIMARY KEY NOT NULL,
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
  id                           BIGSERIAL PRIMARY KEY NOT NULL,
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
  FOREIGN KEY (house_id) REFERENCES house (id),
  FOREIGN KEY (notification_type_id) REFERENCES notification_type (id)
);
CREATE TABLE notification_count
(
  id      BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT,
  count   BIGINT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  UNIQUE (user_id)
);

CREATE TABLE lease_tenant
(
  id           BIGSERIAL PRIMARY KEY NOT NULL,
  lease_id     BIGINT,
  tenant_id    BIGINT,
  joined_date  TIMESTAMP,
  leave_date   TIMESTAMP,
  lease_status BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  FOREIGN KEY (lease_id) REFERENCES lease (id),
  UNIQUE (lease_id, tenant_id)
);

CREATE TABLE payment_card
(
  id               BIGSERIAL PRIMARY KEY NOT NULL,
  user_id          BIGINT,
  card_update_time TIMESTAMP,
  name_on_card     VARCHAR(100),
  card_number      VARCHAR(500),
  expiry_date      VARCHAR(5),
  cvv_number       VARCHAR(500),
  card_type        VARCHAR(100),
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  UNIQUE (user_id)
);