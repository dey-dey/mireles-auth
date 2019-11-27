CREATE TABLE role
(
    id bigserial NOT NULL,
    role VARCHAR(120) NOT NULL,
    role_description VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);