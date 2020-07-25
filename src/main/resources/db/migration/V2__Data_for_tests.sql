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

INSERT INTO article_categories(
	name, created, last_updated)
	VALUES ('Camping', '2020-07-19', '2020-07-19'),
	('Diving', '2020-07-19', '2020-07-19'),
	('Hostels', '2020-07-19', '2020-07-19'),
	('Fishing', '2020-07-19', '2020-07-19');

	INSERT INTO articles(
	author_id, city_id, created, last_updated, language_id, title, text, category_id)
	VALUES
	(1, 1, '2020-01-01', '2020-01-01',1,'title1','text1', 1),
	(2, 2, '2020-02-02', '2020-02-02',2,'title2','text2', 2),
	(3, 3, '2020-03-03', '2020-03-03',3,'title3','text3', 3),
	(1, 1, '2020-04-04', '2020-04-04',4,'title4','text4', 1),
	(6, 2, '2019-09-04', '2019-09-10',1,'title5','text5', 3);

	INSERT INTO travelmatch.articles_tags(
	article_id, tag_id)
	VALUES (1, 1),
	(2, 2),(2,3),
	(3, 3),(3,4),
	(4, 1),(4, 2),(4, 3)
	;

	INSERT INTO travelmatch.article_likes(
	article_id, user_id,value)
	VALUES (1, 1,1),
	(2, 2,1),(2,3,-1),
	(3, 3,1),(3,4,1),
	(4, 1,-1),(4, 2,-1),(4, 3,1)
	;

	INSERT INTO travelmatch.article_rating(
	article_id, user_id,value)
	VALUES (1, 1,1),(1, 2,2),(1, 3,3),(1, 4,4),(1,5,5),
	(2, 2,2),(2,3,3),(2, 4,4),(2,5,5),
	(3, 3,5),(3,4,5),(3, 5,5),(3,6,4),
	(4, 1,2),(4, 2,2)
	;
-- запрос для определения количества поставленных оценок для рейтинга статей
select
        distinct article0_.id as id1_7_,
		ratings1_.article_id as article_id1,
		sum(case
            when ratings1_.article_id is null then 0
            else 1
        end) as countValues,
        article0_.author_id as author_i6_7_,
        article0_.category_id as category7_7_,
        article0_.city_id as city_id8_7_,
        article0_.created as created2_7_,
        article0_.language_id as language9_7_,
        article0_.last_updated as last_upd3_7_,
        article0_.text as text4_7_,
        article0_.title as title5_7_
    from
        travelmatch.articles article0_
    left outer join
        travelmatch.article_rating ratings1_
            on article0_.id=ratings1_.article_id
    group by
        ratings1_.article_id ,
        article0_.id
 ;

--  запросы, чтобы наладить комбинированный запрос по рейтингам и лайкам
 SELECT article_id,
avg(value) as rating
	FROM travelmatch.article_rating
	group by(article_id)
	order by article_id;

	SELECT article_id,
sum(case when value=1 then 1 else 0 end) as likes,
sum(case when value=-1 then 1 else 0 end) as dislikes
	FROM travelmatch.article_likes

	group by(article_id)
	order by article_id;

	    select
        distinct article0_.id as article_id,
		 sum(case
            when coalesce(likes1_.value, 0)=1 then 1
            else 0
        end) as countLikes,
        avg(coalesce(ratings2_.value, 0)) as rating

    from
        travelmatch.articles article0_
    left outer join
        travelmatch.article_likes likes1_
            on article0_.id=likes1_.article_id
    left outer join
        travelmatch.article_rating ratings2_
            on article0_.id=ratings2_.article_id
    group by
        ratings2_.article_id ,
        article0_.id
    order by
        article0_.id asc;