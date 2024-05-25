CREATE TABLE Account (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    account_locked BOOLEAN,
    enabled BOOLEAN,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP
);

CREATE TABLE Role (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP
);

CREATE TABLE Token (
    id UUID PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    created_at TIMESTAMP,
    expires_at TIMESTAMP,
    validated_at TIMESTAMP,
    account_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

CREATE TABLE account_roles (
    account_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES Account(id),
    FOREIGN KEY (role_id) REFERENCES Role(id)
);