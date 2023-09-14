INSERT INTO mamotec_user (id, first_name, last_name, email, username, password, role, created_at, updated_at)
VALUES (default, 'MaMoTec', 'Admin', 'mamotec@outlook.de', 'mamotec', '$2a$10$FhT1lQ2Iny/Z6nuxCdhOJOppO/f11GalVwUg5BljPLsH7Dngi3YAy', 0, now(), NULL);

insert into system_configuration (id, direct_marketing, feed_in_management, created_at, updated_at)
values (default, false, false, now(), NULL);
