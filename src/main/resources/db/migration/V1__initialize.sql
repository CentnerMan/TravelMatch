DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  username              varchar (50) NOT NULL UNIQUE,
  password              VARCHAR(80),
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  user_id               bigint NOT NULL,
  role_id               bigint NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (username, password, first_name, last_name, email)
VALUES
('admin','$2a$10$93.GvO4rU88tUMM8DYqi9OIlStKWPyByqx9mQ9AZWCAmRilrC6X5i','Adminn','Adminoff','admin@gmail.com');
-- password=12345

INSERT INTO users (username, password, first_name, last_name, email)
VALUES
('user','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','Us','Er','user@mail.com');
-- password=11111

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2);

