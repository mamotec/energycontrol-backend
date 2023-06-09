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
    id              SERIAL PRIMARY KEY,
    device_class TEXT,
    interface_type  TEXT,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP
);

CREATE TABLE device
(
    id                  SERIAL PRIMARY KEY,
    name                TEXT,
    description         TEXT,
    serial_number       TEXT,
    interface_config_id INTEGER,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP,
    CONSTRAINT fk_interface FOREIGN KEY (interface_config_id) REFERENCES interface_config (id)
);

CREATE TABLE device_data
(
    id              SERIAL PRIMARY KEY,
    device_id       INTEGER,
    power_generated NUMERIC(10, 2),
    voltage         NUMERIC(10, 2),
    current         NUMERIC(10, 2),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP,
    CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES device (id)
);
