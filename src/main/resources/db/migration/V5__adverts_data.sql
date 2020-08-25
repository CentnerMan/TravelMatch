delete from advert_stats;
delete from advert_schedules;
delete from adverts;

insert into adverts (title,short_text,long_text,user_id,created,last_updated,city_id,price,currency_id,is_actual)
values ('Java book', 'Best of the best java book','Best of the best java book in detail',1,current_timestamp,current_timestamp,1,100,1,true);

insert into adverts (title,short_text,long_text,user_id,created,last_updated,city_id,price,currency_id,is_actual)
values ('Php book', 'Php book description','Php book description in detail',1,current_timestamp,current_timestamp,1,200,1,true);

insert into adverts (title,short_text,long_text,user_id,created,last_updated,city_id,price,currency_id,is_actual)
values ('C# book', 'C# book description','C# book description in detail', 1,current_timestamp,current_timestamp,1,300,1,true);

insert into advert_stats(advert_id, rating, reviews) values (1,2.5,2);

insert into advert_schedules(advert_id,begin_date,end_date,price,currency_id)
values (1,'01-01-2020 07:00','01-01-2020 08:00',100,1);

insert into advert_schedules(advert_id,begin_date,end_date,price,currency_id)
values (1,'01-01-2020 08:00','01-01-2020 09:00',200,1);

insert into advert_reviews(advert_id, user_id, text,last_updated) values (1,1,'Very good',current_timestamp);

insert into advert_reviews(advert_id, user_id, text,last_updated) values (1,2,'Perfect',current_timestamp);