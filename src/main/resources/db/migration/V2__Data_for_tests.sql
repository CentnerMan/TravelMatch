INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (username, password, first_name, last_name, email, birthday, phone_number, sex,about)
VALUES
('admin','$2a$10$93.GvO4rU88tUMM8DYqi9OIlStKWPyByqx9mQ9AZWCAmRilrC6X5i','Adminn','Adminoff','admin@gmail.com','1990-01-01','+77777777777','MALE','Hi! I like IT JAVA Programming BEER'),
-- password=12345
('user','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','Us','Er','user@mail.com','2000-01-01','+11111111111','MALE','Like to see new places, meet with new people. And beer.'),
-- password=11111
('user3','$2a$10$93.GvO4rU88tUMM8DYqi9OIlStKWPyByqx9mQ9AZWCAmRilrC6X5i','Vova','Victorov','vova@gmail.com','1980-07-02','+77777777771','MALE','Hi! I like to travel'),
-- password=12345
('user4','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','Lena','Ivanova','cat@mail.com','2011-11-24','+11111111112','FEMALE','I like animals, cartoons'),
-- password=11111
('user5','$2a$10$93.GvO4rU88tUMM8DYqi9OIlStKWPyByqx9mQ9AZWCAmRilrC6X5i','Oleg','Crig','oleg@gmail.com','1980-07-02','+77777777773','MALE','Hi! I like to travel'),
-- password=12345
('user6','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','Zina','Petrova','zina@mail.com','1951-11-24','+11111111114','FEMALE','I like animals, cartoons');
-- password=11111

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2);

INSERT INTO tags (name)
VALUES ('отели'),('пляжи'),('одежда'),('достопримечательности'),('национальная кухня'),('Париж'),('Франция'),('Италия'),
('Коллизей'),('Милан'),('Эйфелева башня'),('Лувр'),('Дуомо'),('Рим');

INSERT INTO travelmatch.users_tags (user_id, tag_id)
VALUES
(1,1), (1,3),(1,5),(1,7),(1,9),
(2,2), (2,4),(2,6),(1,8),
(4,4),(4,8),
(5,5),
(6,6),(6,4),(6,8);

INSERT INTO currencies (name)
VALUES ('RUR'),('EURO'),('USD');

INSERT INTO languages (name)
VALUES ('english'),('русский'),('italiano'),('Español'),('Deutsche'),('français');

INSERT INTO countries (name)
VALUES ('Италия'),('Франция'),('Россия'),('Испания'),('Германия'),('Португалия'),('Греция');

INSERT INTO cities (name,country_id)
VALUES ('Париж',2),('Рим',1),('Москва',3);

INSERT INTO reason_claims(name)
VALUES ('Не отвечает телефон'),('Продано'),('Офис переехал'),('Закрыто на ремонт'),
('Описание не соответствует действительности'),('Другое');

INSERT INTO language_skills(user_id,language_id,value)
VALUES
(1,1,5),(1,2,5),(1,3,3),
(2,1,4),(2,2,5),(2,3,5),
(3,1,3),(3,2,5),(3,3,4),
(4,1,5),(4,2,5),
(5,1,4),(5,2,5),(5,6,5),
(6,1,3),(6,2,5),(6,6,4);
-- запрос для просмотра тегов пользователей
select users.id,users.username, tags.name, tags.id as tag_id from travelmatch.users as users
	inner join travelmatch.users_tags as users_tags
	on users_tags.user_id = users.id
	inner join travelmatch.tags as tags
	on users_tags.tag_id = tags.id
	order by tags.name, users.id;
