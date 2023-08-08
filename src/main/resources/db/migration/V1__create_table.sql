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
    protocol_id bigint,
    type        TEXT,
    port        TEXT,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP
);

CREATE TABLE device
(
    id                  SERIAL PRIMARY KEY,
    name                TEXT,
    description         TEXT,
    serial_number       TEXT,
    unit_id             INTEGER,
    interface_config_id INTEGER,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP,
    CONSTRAINT fk_interface_config FOREIGN KEY (interface_config_id) REFERENCES interface_config (id)
);

CREATE TABLE device_data
(
    id         SERIAL PRIMARY KEY,
    device_id  INTEGER,
    type       TEXT,
    value      TEXT,
    unit       TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES device (id)
);
