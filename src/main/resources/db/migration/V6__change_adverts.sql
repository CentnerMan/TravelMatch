DROP TABLE IF EXISTS adverts cascade;

CREATE TABLE adverts
(
    id                bigserial,
	title             VARCHAR(255) NOT NULL,
	short_text        VARCHAR(2000) NOT NULL,
    long_text         VARCHAR(10000) NOT NULL,
    user_id           bigint    NOT NULL,
    created           timestamp NOT NULL,
    last_updated      timestamp NOT NULL,
    city_id           bigint     NULL,
	price             bigint     NULL,
    currency_id       bigint     NULL,
    is_actual         boolean    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (city_id) REFERENCES cities (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

DROP TABLE IF EXISTS advert_categories;

DROP TABLE IF EXISTS advert_schedules;

create table advert_schedules(
	id                bigserial,
	advert_id  		  bigint,
	begin_date        timestamp,
    end_date          timestamp,
	price             bigint     NULL,
    currency_id       bigint     NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (advert_id) REFERENCES adverts (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id)
);
DROP TABLE IF EXISTS advert_stats;

create table advert_stats(
  advert_id  bigint,
  rating     float NULL,
  reviews bigint NULL,
  FOREIGN KEY (advert_id) REFERENCES adverts (id)
);

DROP TABLE IF EXISTS advert_reviews;
create table advert_reviews(
id bigserial,
advert_id  		  bigint    NOT NULL,
user_id           bigint    NOT NULL,
text              VARCHAR(10000) NOT NULL,
last_updated      timestamp NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (advert_id) REFERENCES adverts (id),
FOREIGN KEY (user_id) REFERENCES users (id)
)