DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  about                 varchar (10000),
  birthday              timestamp NOT NULL,
  username              varchar (50) NOT NULL UNIQUE,
  password              VARCHAR(80),
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  created               timestamp NULL,
  last_updated          timestamp NULL,
  phone_number          VARCHAR (12) NOT NULL UNIQUE,
  sex                   VARCHAR (6) NOT NULL CHECK (sex in ('MALE','FEMALE')),
  status_activity       VARCHAR (8),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
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

DROP TABLE IF EXISTS tags;
CREATE TABLE tags (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_tags;
CREATE TABLE users_tags (
  user_id           bigint NOT NULL,
  tag_id            bigint NOT NULL,
  PRIMARY KEY (user_id, tag_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (tag_id)
  REFERENCES tags (id)
);

DROP TABLE IF EXISTS currencies;
CREATE TABLE currencies (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS languages;
CREATE TABLE languages (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS language_skills;
CREATE TABLE language_skills (
  user_id               bigint NOT NULL,
  language_id           bigint NOT NULL,
  value                 integer NOT NULL CHECK (value>0 AND value <=5),
  created               timestamp,
  last_updated          timestamp,
  PRIMARY KEY (user_id,language_id),
  FOREIGN KEY(language_id)
  REFERENCES languages (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

DROP TABLE IF EXISTS countries;
CREATE TABLE countries (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cities;
CREATE TABLE cities (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  country_id            bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (country_id)
  REFERENCES countries
);

DROP TABLE IF EXISTS article_categories;
CREATE TABLE article_categories (
  id                    bigserial,
  name                  VARCHAR(100) NOT NULL,
  created               timestamp,
  last_updated          timestamp,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS articles;
CREATE TABLE articles (
    id                    bigserial,
    author_id             bigint NULL,
    city_id               bigint NULL,
    created               timestamp,
    last_updated          timestamp,
    language_id           bigint NULL,
    title                 VARCHAR(255),
    text                  VARCHAR(10000000),
    category_id           bigint NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (city_id) REFERENCES cities (id),
    FOREIGN KEY (language_id) REFERENCES languages (id),
    FOREIGN KEY (category_id) REFERENCES article_categories (id)
);


DROP TABLE IF EXISTS article_likes;
CREATE TABLE article_likes (
  article_id            bigint NOT NULL,
  user_id               bigint NOT NULL,
  value                 integer NOT NULL,
  created               timestamp,
  last_updated          timestamp,
  PRIMARY KEY (article_id,user_id),
  FOREIGN KEY(article_id)
  REFERENCES articles (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
    id                    bigserial,
    created               timestamp,
    last_updated          timestamp,
    article_id            bigint NOT NULL,
    user_id               bigint NOT NULL,
    text                  VARCHAR(10000),
    parent_id             bigint NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(article_id)
    REFERENCES articles (id),
    FOREIGN KEY (user_id)
    REFERENCES users (id),
    FOREIGN KEY (parent_id)
    REFERENCES comments(id)
);

DROP TABLE IF EXISTS articles_tags;
CREATE TABLE articles_tags (
  article_id           bigint NOT NULL,
  tag_id               bigint NOT NULL,
  PRIMARY KEY (article_id, tag_id),
  FOREIGN KEY (article_id)
  REFERENCES articles (id),
  FOREIGN KEY (tag_id)
  REFERENCES tags (id)
);

DROP TABLE IF EXISTS article_rating;
CREATE TABLE article_rating (
    article_id            bigint NOT NULL,
    user_id               bigint NOT NULL,
    value                 integer NOT NULL,
    created               timestamp,
    last_updated          timestamp,
    PRIMARY KEY (article_id,user_id),
    FOREIGN KEY(article_id)
    REFERENCES articles (id),
    FOREIGN KEY (user_id)
    REFERENCES users (id)
);

DROP TABLE IF EXISTS favorite_articles;
CREATE TABLE favorite_articles (
  article_id           bigint NOT NULL,
  user_id              bigint NOT NULL,
  PRIMARY KEY (article_id, user_id),
  FOREIGN KEY (article_id)
  REFERENCES articles (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

DROP TABLE IF EXISTS users_pictURLs;
CREATE TABLE users_pictURLs (
  user_id              bigint NOT NULL,
  pictURL              VARCHAR(255),
  PRIMARY KEY(user_id,pictURL),
  FOREIGN KEY(user_id)
  REFERENCES users(id)
);

DROP TABLE IF EXISTS advert_categories;
CREATE TABLE advert_categories (
  id                    bigserial,
  name                  VARCHAR(100) NOT NULL UNIQUE,
  created               timestamp,
  last_updated          timestamp,
  product_type          VARCHAR (7) NOT NULL CHECK (product_type in ('PRODUCT','SERVICE')),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS adverts;
CREATE TABLE adverts (
    id                    bigserial,
    begin_date            timestamp,
    end_date              timestamp,
    category_id           bigint NOT NULL,
    city_id               bigint NULL,
    currency_id           bigint NOT NULL,
    created               timestamp,
    is_actual             boolean NOT NULL,
    last_updated          timestamp,
    language_id           bigint NULL,
    price                 bigint NULL,
    product_type          VARCHAR (7) NOT NULL CHECK (product_type in ('PRODUCT','SERVICE')),
    product_condition     VARCHAR (4) NULL CHECK (product_condition in ('NEW','USED')),
    title                 VARCHAR(255),
    text                  VARCHAR(10000),
    type                  VARCHAR(4) NOT NULL CHECK (type in ('BUY','SALE')),
    user_id               bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (city_id) REFERENCES cities (id),
    FOREIGN KEY (language_id) REFERENCES languages (id),
    FOREIGN KEY (category_id) REFERENCES advert_categories (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

DROP TABLE IF EXISTS adverts_tags;
CREATE TABLE adverts_tags (
  advert_id         bigint NOT NULL,
  tag_id            bigint NOT NULL,
  PRIMARY KEY (advert_id, tag_id),
  FOREIGN KEY (advert_id)
  REFERENCES adverts (id),
  FOREIGN KEY (tag_id)
  REFERENCES tags (id)
);

DROP TABLE IF EXISTS favorite_adverts;
CREATE TABLE favorite_adverts (
  advert_id           bigint NOT NULL,
  user_id              bigint NOT NULL,
  PRIMARY KEY (advert_id, user_id),
  FOREIGN KEY (advert_id)
  REFERENCES adverts (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

DROP TABLE IF EXISTS adverts_pictURLs;
CREATE TABLE adverts_pictURLs (
  advert_id              bigint NOT NULL,
  pictURL                VARCHAR(255),
  PRIMARY KEY(advert_id,pictURL),
  FOREIGN KEY(advert_id)
  REFERENCES adverts(id)
);

DROP TABLE IF EXISTS reason_claims;
CREATE TABLE reason_claims (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS advert_claims;
CREATE TABLE advert_claims (
  id                    bigserial,
  advert_id             bigint NOT NULL,
  created               timestamp NULL,
  last_updated          timestamp NULL,
  text                  VARCHAR(255) NULL,
  user_id               bigint NOT NULL,
  reason_claim_id       bigint NOT NULL,
  status                VARCHAR (6) NOT NULL CHECK (status in ('CLOSED','NEW')),
  PRIMARY KEY (id),
  FOREIGN KEY (advert_id) REFERENCES adverts(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (reason_claim_id) REFERENCES reason_claims(id)
);

DROP TABLE IF EXISTS match_profiles;
CREATE TABLE match_profiles (
    id                    bigserial,
    begin_date            timestamp,
    end_date              timestamp,
    city_id               bigint NULL,
    currency_id           bigint NOT NULL,
    created               timestamp,
    is_actual             boolean NOT NULL,
    last_updated          timestamp,
    language_id           bigint NULL,
    price                 bigint NULL,
    target_status         VARCHAR (8) NOT NULL CHECK (target_status in ('LOCAL','TRAVELER')),
    text                  VARCHAR(10000),
    user_id               bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (city_id) REFERENCES cities (id),
    FOREIGN KEY (language_id) REFERENCES languages (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

DROP TABLE IF EXISTS match_requests;
CREATE TABLE match_requests (
    id                    bigserial,
    match_profile_id      bigint NOT NULL,
    owner_id              bigint NOT NULL,
    customer_id           bigint NOT NULL,
    created               timestamp,
    last_updated          timestamp,
    status                VARCHAR (8) NOT NULL CHECK (status in ('NEW','ACCEPTED','REFUSED')),
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (customer_id) REFERENCES users (id),
    FOREIGN KEY (match_profile_id) REFERENCES match_profiles (id)
);

DROP TABLE IF EXISTS favorite_match_profiles;
CREATE TABLE favorite_match_profiles (
  match_profile_id     bigint NOT NULL,
  user_id              bigint NOT NULL,
  PRIMARY KEY (match_profile_id, user_id),
  FOREIGN KEY (match_profile_id)
  REFERENCES match_profiles (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

DROP TABLE IF EXISTS match_profiles_tags;
CREATE TABLE match_profiles_tags (
  match_profile_id  bigint NOT NULL,
  tag_id            bigint NOT NULL,
  PRIMARY KEY (match_profile_id, tag_id),
  FOREIGN KEY (match_profile_id)
  REFERENCES match_profiles (id),
  FOREIGN KEY (tag_id)
  REFERENCES tags (id)
);

DROP TABLE IF EXISTS message_objects;
CREATE TABLE message_objects(
    id                    bigserial,
    match_profile_id      bigint NULL,
    advert_id             bigint NULL,
    is_actual             boolean NOT NULL,
    created               timestamp,
    last_updated          timestamp,
    finish_date           timestamp,
    finish_reason         VARCHAR (255),
    finish_user_id        bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (finish_user_id) REFERENCES users (id),
    FOREIGN KEY (match_profile_id) REFERENCES match_profiles (id),
    FOREIGN KEY (advert_id) REFERENCES adverts (id)
);

DROP TABLE IF EXISTS messages;
CREATE TABLE messages(
    id                    bigserial,
    created               timestamp,
    last_updated          timestamp,
    sender_id             bigint NOT NULL,
    receiver_id           bigint NOT NULL,
    reason_id             bigint NOT NULL,
    text                  VARCHAR (10000) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (receiver_id) REFERENCES users (id),
    FOREIGN KEY (reason_id) REFERENCES message_objects (id)
);

