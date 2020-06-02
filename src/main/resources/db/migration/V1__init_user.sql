

CREATE TABLE tenant (
    id uuid NOT NULL,
    enabled boolean NOT NULL,
    name varchar(250),
    type varchar(250),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE users (
    id uuid NOT NULL,
    default_email varchar(250) NOT NULL,
    first_name varchar(250),
    last_name varchar(250),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id bigserial NOT NULL,
    user_id uuid NOT NULL references users (id) on delete cascade,
    tenant_id uuid NOT NULL references tenant (id) on delete cascade,
    email varchar(250) NOT NULL,
    phone_number varchar(15),
    is_primary boolean not null,
    enabled boolean not null,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE tenant_member
(
    id bigserial NOT NULL,
    member_id bigint NOT NULL references member on delete cascade,
    tenant_id uuid NOT NULL references tenant on delete cascade,
    starting_on TIMESTAMPTZ NOT NULL DEFAULT now(),
    retired_on TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tenant_member_role
(
    id bigserial NOT NULL,
    tenant_member_id bigint NOT NULL references tenant_member on delete cascade,
    starting_on TIMESTAMPTZ NOT NULL DEFAULT now(),
    retired_on TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    primary key (id)
);

CREATE UNIQUE index part_of_email on users (default_email);