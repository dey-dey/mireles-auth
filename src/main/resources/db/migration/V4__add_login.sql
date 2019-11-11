CREATE TABLE authentication
(
    id bigserial NOT NULL,
    member_id      bigint       NOT NULL references member on delete cascade,
    active             boolean      not null,
    password_hash      varchar(255) NOT NULL,
    password_algorithm varchar(255) NOT NULL,
    password_salt      varchar(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE authentication_attempt
(
    id bigserial NOT NULL,
    authentication_information_id bigint NOT NULL references authentication on delete cascade,
    successful varchar(36)  NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    user_agent varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE token
(
    id bigserial NOT NULL,
    member_id bigint NOT NULL references member on delete cascade,
    type          VARCHAR(120) NOT NULL,
    token         varchar(36)  NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);


CREATE TABLE token_confirmation
(
    id bigserial  NOT NULL,
    member_id bigint NOT NULL references member on delete cascade,
    type          varchar(120) NOT NULL,
    token         varchar(36)  NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);