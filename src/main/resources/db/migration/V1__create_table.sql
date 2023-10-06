CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE mamotec_user
(
    id         BIGINT       NOT NULL DEFAULT NEXTVAL('user_id_seq'),
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       integer,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_email_unique UNIQUE (email),
    CONSTRAINT user_username_unique UNIQUE (username)
);


CREATE TABLE interface_config
(
    id          SERIAL PRIMARY KEY,
    type        TEXT,
    description TEXT,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP
);

CREATE TABLE device_group
(
    id               SERIAL PRIMARY KEY,
    name             TEXT,
    type             TEXT,
    deleted BOOLEAN NOT NULL DEFAULT false,
    created_at       TIMESTAMP NOT NULL,
    updated_at       TIMESTAMP
);

CREATE TABLE plant_device_group
(
    id               SERIAL PRIMARY KEY,
    direct_marketing   BOOLEAN,
    feed_in_management BOOLEAN,
    peak_kilowatt      INTEGER NOT NULL
    );

CREATE TABLE home_device_group
(
    id            SERIAL PRIMARY KEY,
    peak_kilowatt INTEGER NOT NULL
);

CREATE TABLE battery_device_group
(
    id            SERIAL PRIMARY KEY
);

CREATE TABLE charging_station_device_group
(
    id            SERIAL PRIMARY KEY
);

CREATE TABLE heat_pump_device_group
(
    id            SERIAL PRIMARY KEY
);

CREATE TABLE device
(
    id                  SERIAL PRIMARY KEY,
    interface_config_id INTEGER,
    unit_id INTEGER,
    priority INTEGER,
    name                TEXT,
    manufacturer_id INTEGER,
    device_id       INTEGER,
    device_type     TEXT,
    active              BOOLEAN   NOT NULL DEFAULT false,
    deleted BOOLEAN NOT NULL DEFAULT false,
    host TEXT,
    port TEXT,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP,
    device_group_id     INTEGER,
    CONSTRAINT fk_interface_config FOREIGN KEY (interface_config_id) REFERENCES interface_config (id),
    CONSTRAINT fk_device_group_id FOREIGN KEY (device_group_id) REFERENCES device_group (id)
);

CREATE TABLE charging_station_device
(
    id      SERIAL PRIMARY KEY,
    device_id_charger INTEGER,
    ocpp_available BOOLEAN NOT NULL DEFAULT false,
    uuid uuid,
    charge_point_status TEXT
);

CREATE TABLE hybrid_inverter_device
(
    id      SERIAL PRIMARY KEY
);

CREATE TABLE system_configuration
(
    id               SERIAL PRIMARY KEY,
    direct_marketing BOOLEAN   not null default false,
    feed_in_management BOOLEAN not null default false,
    created_at       TIMESTAMP NOT NULL,
    updated_at       TIMESTAMP
);