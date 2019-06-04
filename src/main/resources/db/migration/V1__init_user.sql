CREATE TABLE users (
  id bigserial NOT NULL,
  username varchar(250) NOT NULL,
  first_name varchar(250),
  last_name varchar(250),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE index part_of_email on users (username);