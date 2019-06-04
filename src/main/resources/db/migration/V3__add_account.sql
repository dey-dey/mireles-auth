CREATE TABLE account
(
    id bigserial NOT NULL,
    plan_level VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE company
(
    id bigserial NOT NULL,
    account_id bigint not null references account on delete restrict,
    name VARCHAR(120) NOT NULL,
    access_level VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE membership
(
    id bigserial NOT NULL,
    user_id bigint NOT NULL references users (id) on delete cascade,
    company_id bigint NOT NULL references company on delete cascade,
    email varchar(250) NOT NULL,
    type varchar(120) NOT NULL,
    is_primary boolean not null,
    verification varchar(120),
    phone_number varchar(15) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE role
(
    id bigserial NOT NULL,
    role VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE membership_role
(
    id bigserial NOT NULL,
    role_id bigserial NOT NULL references role on delete cascade,
    membership_id bigserial NOT NULL references membership on delete cascade,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);