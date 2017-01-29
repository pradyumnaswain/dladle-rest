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
  id   SERIAL PRIMARY KEY  NOT NULL,
  name VARCHAR(128)        NOT NULL
);

/*service_type*/
CREATE TABLE service_type
(
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
);

/*amenities_type*/
CREATE TABLE amenities_type
(
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(128)       NOT NULL
);
/*place_type*/
CREATE TABLE place_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);
/*bedroom_type*/
CREATE TABLE bedroom_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);

/*Home View Type*/
CREATE TABLE home_view_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);

/*Years of Experience*/
CREATE TABLE years_exp
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);

/* User*/
CREATE TABLE user_dladle
(
  id                SERIAL PRIMARY KEY  NOT NULL,
  emailId           VARCHAR(100)        NOT NULL,
  password          VARCHAR(500)        NOT NULL,
  user_type_id      INT,
  verified          BOOLEAN,
  verification_code VARCHAR(100),
  otp               INT,
  first_name        VARCHAR(20),
  last_name         VARCHAR(20),
  id_number         VARCHAR(45),
  cell_number       INT,
  FOREIGN KEY (user_type_id) REFERENCES user_type (id)
);
CREATE UNIQUE INDEX user_dladle_email_uindex
  ON user_dladle (emailid);
/*landlord*/
CREATE TABLE landlord
(
  id          SERIAL PRIMARY KEY NOT NULL,
  user_id     INT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id)
);

/*property*/
CREATE TABLE property
(
  id                SERIAL PRIMARY KEY NOT NULL,
  landlord_id       INT,
  location          VARCHAR(100),
  address           VARCHAR(100),
  place_name        VARCHAR(100),
  place_type_id     INT,
  complex_name      VARCHAR(45),
  unit_number       VARCHAR(20),
  bedroom_type_id   INT,
  image_url         VARCHAR(100),
  home_view_type_id INT,
  FOREIGN KEY (place_type_id) REFERENCES place_type (id),
  FOREIGN KEY (bedroom_type_id) REFERENCES bedroom_type (id),
  FOREIGN KEY (home_view_type_id) REFERENCES home_view_type (id),
  FOREIGN KEY (landlord_id) REFERENCES landlord (id)
);

/*house*/
CREATE TABLE house
(
  id           SERIAL PRIMARY KEY NOT NULL,
  house_number INT UNIQUE         NOT NULL,
  property_id  INT,
  FOREIGN KEY (property_id) REFERENCES property (id)
);

/*amenities*/
CREATE TABLE amenities
(
  id                SERIAL PRIMARY KEY NOT NULL,
  amenities_type_id INT,
  count             INT,
  house_id          INT,
  FOREIGN KEY (amenities_type_id) REFERENCES amenities_type (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
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
  description VARCHAR(100),
  FOREIGN KEY (rating_id) REFERENCES rating (id)
);

/*tenant*/
CREATE TABLE tenant
(
  id          SERIAL PRIMARY KEY NOT NULL,
  user_id     INT,
  landlord_id INT,
  house_id    INT,
  FOREIGN KEY (user_id) REFERENCES user_dladle (id),
  FOREIGN KEY (landlord_id) REFERENCES landlord (id),
  FOREIGN KEY (house_id) REFERENCES house (id)
);
/*tenant*/
CREATE TABLE vendor
(
  id               SERIAL PRIMARY KEY NOT NULL,
  business_name    VARCHAR(20),
  user_id          INT,
  business_address VARCHAR(50),
  service_type_id  INT,
  transport        BOOLEAN,
  tools            BOOLEAN,
  experience_id    INT,
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (experience_id) REFERENCES years_exp (id),
  FOREIGN KEY (user_id) REFERENCES user_dladle (id)
);

/*lease*/
CREATE TABLE lease
(
  id           SERIAL PRIMARY KEY NOT NULL,
  landlord_id  INT,
  tenant_id    INT,
  lease_date   DATE,
  renewal_date DATE,
  remark       VARCHAR(100),
  FOREIGN KEY (landlord_id) REFERENCES landlord (id),
  FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);
/*service*/
CREATE TABLE service
(
  id              SERIAL PRIMARY KEY NOT NULL,
  service_type_id INT,
  vendor_id       INT,
  FOREIGN KEY (service_type_id) REFERENCES service_type (id),
  FOREIGN KEY (vendor_id) REFERENCES vendor (id)
);