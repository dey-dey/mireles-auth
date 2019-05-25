
CREATE TABLE password_reset_token (
  id bigserial NOT NULL ,
  user_id  bigserial NOT NULL,
  token VARCHAR(36) NOT NULL,
  expired TIMESTAMPZ,
  expiry_date TIMESTAMPZ NOT NULL,
  created_at TIMESTAMPZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPZ NOT NULL,
  PRIMARY KEY (id)
) ;


CREATE TABLE verification_token (
  id bigserial NOT NULL ,
  user_id bigserial NOT NULL,
  token VARCHAR(36) NOT NULL,
  expiry_date TIMESTAMPZ NOT NULL,
  expired TIMESTAMPZ,
  created_at TIMESTAMPZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPZ NOT NULL,
  PRIMARY KEY (id)
) ;

