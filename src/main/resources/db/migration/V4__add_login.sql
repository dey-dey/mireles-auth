CREATE TABLE login
(
    id bigserial NOT NULL,
    membership_id      bigint       NOT NULL references membership on delete cascade,
    active             boolean      not null,
    password_hash      varchar(255) NOT NULL,
    password_algorithm varchar(255) NOT NULL,
    password_salt      varchar(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE login_attempt
(
    id bigserial NOT NULL,
    login_id   bigint       NOT NULL references login on delete cascade,
    successful varchar(36)  NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    user_agent varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE token
(
    id bigserial NOT NULL,
    membership_id bigint NOT NULL references membership on delete cascade,
    type          VARCHAR(120) NOT NULL,
    token         varchar(36)  NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);