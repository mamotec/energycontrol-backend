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
    created_at       TIMESTAMP NOT NULL,
    updated_at       TIMESTAMP
);

CREATE TABLE plant_device_group
(
    id               SERIAL PRIMARY KEY,
    direct_marketing BOOLEAN
);

CREATE TABLE device
(
    id                  SERIAL PRIMARY KEY,
    interface_config_id INTEGER,
    name                TEXT,
    manufacturer_id     INTEGER,
    device_id           INTEGER,
    device_type         TEXT,
    unit_id             INTEGER,
    active              BOOLEAN   NOT NULL DEFAULT false,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP,
    device_group_id     INTEGER,
    CONSTRAINT fk_interface_config FOREIGN KEY (interface_config_id) REFERENCES interface_config (id),
    CONSTRAINT fk_device_group_id FOREIGN KEY (device_group_id) REFERENCES device_group (id)
);

CREATE TABLE system_configuration
(
    id               SERIAL PRIMARY KEY,
    direct_marketing BOOLEAN   not null default false,
    created_at       TIMESTAMP NOT NULL,
    updated_at       TIMESTAMP
);