/* Create User*/
-- DROP USER sysadmin;
CREATE USER sysadmin WITH PASSWORD 'masterkey';
/* Create Database*/
-- DROP DATABASE dladle;
CREATE DATABASE dladle WITH OWNER = sysadmin;
/* Grant privileges*/
GRANT ALL PRIVILEGES ON DATABASE dladle TO sysadmin;
/*user_type*/

DROP OWNED BY sysadmin;

CREATE TABLE user_type
(
  id   SERIAL PRIMARY KEY  NOT NULL,
  name VARCHAR(128)        NOT NULL
);
/*user_dladle*/
CREATE TABLE user_dladle
(
  id           SERIAL PRIMARY KEY  NOT NULL,
  emailId      VARCHAR(100)        NOT NULL,
  password     VARCHAR(500)        NOT NULL,
  user_type_id INT,
  verified     BOOLEAN,
  verification_code VARCHAR(100),
  otp VARCHAR(10)
);
/*landlord*/
CREATE TABLE landlord
(
  id              SERIAL PRIMARY KEY NOT NULL,
  first_name      VARCHAR(20),
  last_name       VARCHAR(20),
  identity_number VARCHAR(45),
  user_id         INT
);
/*tenant*/
CREATE TABLE tenant
(
  id              SERIAL PRIMARY KEY NOT NULL,
  first_name      VARCHAR(20),
  last_name       VARCHAR(20),
  identity_number VARCHAR(45),
  user_id         INT,
  landlord_id     INT,
  house_id        INT
);
/*tenant*/
CREATE TABLE vendor
(
  id               SERIAL PRIMARY KEY NOT NULL,
  business_name    VARCHAR(20),
  user_id          INT,
  business_address VARCHAR(50),
  service_type_id  INT
);
/*service_type*/
CREATE TABLE service_type
(
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
);
/*service*/
CREATE TABLE service
(
  id              SERIAL PRIMARY KEY NOT NULL,
  service_type_id INT,
  vendor_id       INT
);
/*amenities_type*/
CREATE TABLE amenities_type
(
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
);
/*amenities*/
CREATE TABLE amenities
(
  id                SERIAL PRIMARY KEY NOT NULL,
  amenities_type_id INT,
  count             INT,
  house_id          INT
);
/*house*/
CREATE TABLE house
(
  id           SERIAL PRIMARY KEY NOT NULL,
  house_number INT UNIQUE         NOT NULL,
  property_id  INT
);
/*property*/
CREATE TABLE property
(
  id          SERIAL PRIMARY KEY NOT NULL,
  landlord_id INT,
  location    VARCHAR(100),
  address     VARCHAR(100)
);
/*lease*/
CREATE TABLE lease
(
  id           SERIAL PRIMARY KEY NOT NULL,
  landlord_id  INT,
  tenant_id    INT,
  lease_date   DATE,
  renewal_date DATE,
  remark       VARCHAR(100)
);
/*rating*/
CREATE TABLE rating
(
  id      SERIAL PRIMARY KEY NOT NULL,
  user_id INT,
  value   DECIMAL
);

/*comments*/
CREATE TABLE rating_comments
(
  id          SERIAL PRIMARY KEY NOT NULL,
  rating_id   INT,
  user_id     INT,
  description VARCHAR(100)
);

CREATE TABLE place_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);
CREATE TABLE bedroom_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);

CREATE TABLE homeview_type
(
  id SERIAL PRIMARY KEY,
  name VARCHAR (20)
);

CREATE UNIQUE INDEX user_dladle_email_uindex
  ON user_dladle (emailid);
ALTER TABLE user_dladle
  ADD CONSTRAINT user_type_id FOREIGN KEY (user_type_id) REFERENCES user_type (id);
ALTER TABLE landlord
  ADD CONSTRAINT user_vendor_id FOREIGN KEY (user_id) REFERENCES user_dladle (id);
ALTER TABLE tenant
  ADD CONSTRAINT user_tenant_id FOREIGN KEY (user_id) REFERENCES user_dladle (id);
ALTER TABLE tenant
  ADD CONSTRAINT landlord_id FOREIGN KEY (landlord_id) REFERENCES landlord (id);
ALTER TABLE tenant
  ADD CONSTRAINT house_id FOREIGN KEY (house_id) REFERENCES house (id);
ALTER TABLE vendor
  ADD CONSTRAINT user_vendor_id FOREIGN KEY (user_id) REFERENCES user_dladle (id);
ALTER TABLE service
  ADD CONSTRAINT service_type_id FOREIGN KEY (service_type_id) REFERENCES service_type (id);
ALTER TABLE service
  ADD CONSTRAINT vendor_id FOREIGN KEY (vendor_id) REFERENCES vendor (id);
ALTER TABLE house
  ADD CONSTRAINT property_id FOREIGN KEY (property_id) REFERENCES property (id);
ALTER TABLE property
  ADD CONSTRAINT landlord_property_id FOREIGN KEY (landlord_id) REFERENCES landlord (id);
ALTER TABLE lease
  ADD CONSTRAINT landlord_lease_id FOREIGN KEY (landlord_id) REFERENCES landlord (id);
ALTER TABLE lease
  ADD CONSTRAINT tenant_lease_id FOREIGN KEY (tenant_id) REFERENCES tenant (id);
ALTER TABLE rating_comments
  ADD CONSTRAINT rating_id FOREIGN KEY (rating_id) REFERENCES rating (id);
ALTER TABLE amenities
  ADD CONSTRAINT amenities_type_id FOREIGN KEY (amenities_type_id) REFERENCES amenities_type (id);
ALTER TABLE amenities
  ADD CONSTRAINT house_id FOREIGN KEY (house_id) REFERENCES amenities_type (id)
ALTER TABLE vendor
  ADD FOREIGN KEY (service_type_id) REFERENCES service_type (id);

