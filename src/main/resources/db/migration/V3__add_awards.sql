
DROP TABLE IF EXISTS awards;
CREATE TABLE awards (
    id          bigserial,
    title       VARCHAR(255) NOT NULL,
    count       bigint Default 0,
    PRIMARY KEY (id));

DROP TABLE IF EXISTS users_awards;
CREATE TABLE users_awards (
    user_id     bigint NOT NULL,
    award_id    bigint NOT NULL,
    created     timestamp NULL,
    PRIMARY KEY (user_id, award_id),
    FOREIGN KEY (user_id)  REFERENCES users (id),
    FOREIGN KEY (award_id) REFERENCES awards (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_MODERATOR');

INSERT INTO users_roles (user_id, role_id)
VALUES
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2);

INSERT INTO awards (title, count)
VALUES ('100 лайков', 100),('100 коментариев', 100),('Бронь на 10+', 10);

INSERT INTO users_awards (user_id, award_id)
VALUES (2, 1),(2, 2);
