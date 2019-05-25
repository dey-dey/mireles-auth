CREATE TABLE users (
  id bigserial NOT NULL,
  email varchar(250) NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled CHAR(1) DEFAULT NULL,
  created_at TIMESTAMPZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPZ NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE index part_of_email on users (email);

CREATE TABLE role (
  id bigserial NOT NULL,
  role VARCHAR(120) NOT NULL,
  created_at TIMESTAMPZ NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE user_role (
  id bigserial NOT NULL,
  role_id bigserial NOT NULL,
  user_id bigserial NOT NULL,
  created_at TIMESTAMPZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPZ NOT NULL,
  PRIMARY KEY (id)
);