--ROLE TABLE
INSERT INTO role (id, name, description, created_date, last_modified_date)
VALUES ('81803ca6-88bb-421b-a220-90b8c7fe5948', 'USER', 'Pouca permissão', '2024-05-13 17:47:32.028259', null);

INSERT INTO role (id, name, description, created_date, last_modified_date)
VALUES ('d4e46efb-218b-450e-bc9e-6e79ed05f7c7', 'ADMIN', 'Permissão para tudo', '2024-05-13 17:47:32.028259', null);

--USER TABLE
INSERT INTO account (id, email, name, password, enabled, created_date, last_modified_date, account_locked)
VALUES ('e8511df9-43a7-4384-90b1-bb9da544db34', 'ruan@gmail.com', 'Ruan Felipe', '$2a$10$/Ewn34uMOhPGZFvZtOem9e3Nkf6K/CCkjfMZTBfaeufKXWn7AFRlG', 1, '2024-05-13 17:47:32.028259', null, 0);

INSERT INTO account (id, email, name, password, enabled, created_date, last_modified_date, account_locked)
VALUES ('81803ca6-88bb-421b-a220-90b8c7fe5948', 'vitor@gmail.com', 'Vitor Hugo', '$2a$10$/Ewn34uMOhPGZFvZtOem9e3Nkf6K/CCkjfMZTBfaeufKXWn7AFRlG', 1, '2024-05-13 17:47:32.028259', null, 0);

--ACCOUNT_ROLES
INSERT INTO account_roles (users_id, roles_id)
VALUES ('e8511df9-43a7-4384-90b1-bb9da544db34', '81803ca6-88bb-421b-a220-90b8c7fe5948');

INSERT INTO account_roles (users_id, roles_id)
VALUES ('81803ca6-88bb-421b-a220-90b8c7fe5948', '81803ca6-88bb-421b-a220-90b8c7fe5948');
