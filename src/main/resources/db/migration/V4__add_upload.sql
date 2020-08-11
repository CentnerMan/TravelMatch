
DROP TABLE IF EXISTS url_file;
CREATE TABLE url_file (
  id                bigserial,
  sm_url            varchar (100),
  mid_url           varchar (100),
  xl_url            varchar (100),
  origin_url        varchar (100),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS file_upload;
CREATE TABLE file_upload (
  id                bigserial,
  user_id           bigint NOT NULL,
  file_name         varchar (200),
  file_type         varchar (200),
  file_format       varchar (200),
  created           timestamp NULL,
  upload_dir        varchar (200),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id)
--   FOREIGN KEY (upload_dir)
--   REFERENCES url_file
);



