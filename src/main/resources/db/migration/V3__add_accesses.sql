CREATE TABLE role
(
    id bigserial NOT NULL,
    role VARCHAR(120) NOT NULL,
    role_description VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE member_role
(
    id bigserial NOT NULL,
    role_id bigserial NOT NULL references role on delete cascade,
    member_id bigserial NOT NULL references member on delete cascade,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);